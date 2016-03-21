/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE file at the root of the source
 * tree and available online at
 *
 * https://github.com/keeps/roda
 */
package org.roda.core.index;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.roda.core.CorporaConstants;
import org.roda.core.RodaCoreFactory;
import org.roda.core.common.IdUtils;
import org.roda.core.common.RodaUtils;
import org.roda.core.data.adapter.filter.EmptyKeyFilterParameter;
import org.roda.core.data.adapter.filter.Filter;
import org.roda.core.data.adapter.filter.SimpleFilterParameter;
import org.roda.core.data.adapter.sublist.Sublist;
import org.roda.core.data.common.RodaConstants;
import org.roda.core.data.exceptions.AlreadyExistsException;
import org.roda.core.data.exceptions.AuthorizationDeniedException;
import org.roda.core.data.exceptions.EmailAlreadyExistsException;
import org.roda.core.data.exceptions.GenericException;
import org.roda.core.data.exceptions.IllegalOperationException;
import org.roda.core.data.exceptions.NotFoundException;
import org.roda.core.data.exceptions.RODAException;
import org.roda.core.data.exceptions.RequestNotValidException;
import org.roda.core.data.exceptions.UserAlreadyExistsException;
import org.roda.core.data.v2.index.IndexResult;
import org.roda.core.data.v2.ip.AIP;
import org.roda.core.data.v2.ip.AIPState;
import org.roda.core.data.v2.ip.IndexedAIP;
import org.roda.core.data.v2.ip.Representation;
import org.roda.core.data.v2.ip.StoragePath;
import org.roda.core.data.v2.log.LogEntry;
import org.roda.core.data.v2.log.LogEntryParameter;
import org.roda.core.data.v2.risks.Risk;
import org.roda.core.data.v2.user.Group;
import org.roda.core.data.v2.user.RODAMember;
import org.roda.core.data.v2.user.User;
import org.roda.core.data.v2.validation.ValidationException;
import org.roda.core.model.ModelService;
import org.roda.core.storage.DefaultStoragePath;
import org.roda.core.storage.StorageService;
import org.roda.core.storage.fs.FSUtils;
import org.roda.core.storage.fs.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexServiceTest {

  private static Path basePath;
  private static Path logPath;
  private static ModelService model;
  private static IndexService index;

  private static Path corporaPath;
  private static StorageService corporaService;

  private static final Logger LOGGER = LoggerFactory.getLogger(IndexServiceTest.class);

  @BeforeClass
  public static void setUp() throws Exception {

    basePath = Files.createTempDirectory("indexTests");
    System.setProperty("roda.home", basePath.toString());

    boolean deploySolr = true;
    boolean deployLdap = true;
    boolean deployFolderMonitor = false;
    boolean deployOrchestrator = false;
    RodaCoreFactory.instantiateTest(deploySolr, deployLdap, deployFolderMonitor, deployOrchestrator);

    logPath = RodaCoreFactory.getLogPath();
    model = RodaCoreFactory.getModelService();
    index = RodaCoreFactory.getIndexService();

    URL corporaURL = IndexServiceTest.class.getResource("/corpora");
    corporaPath = Paths.get(corporaURL.toURI());
    corporaService = new FileStorageService(corporaPath);

    LOGGER.debug("Running index tests under storage {}", basePath);
  }

  @AfterClass
  public static void tearDown() throws Exception {
    RodaCoreFactory.shutdown();
    FSUtils.deletePath(basePath);
  }

  private void compareAIPWithIndexedAIP(final AIP aip, final IndexedAIP indexedAIP) {
    assertEquals(aip.getId(), indexedAIP.getId());
    assertEquals(aip.getParentId(), indexedAIP.getParentID());
    assertEquals(aip.getPermissions(), indexedAIP.getPermissions());
    assertEquals(aip.isActive(), indexedAIP.getState().equals(AIPState.ACTIVE));
  }

  @Test
  public void testAIPIndexCreateDelete() throws RODAException, ParseException {
    // generate AIP ID
    final String aipId = UUID.randomUUID().toString();

    // Create AIP
    final AIP aip = model.createAIP(aipId, corporaService,
      DefaultStoragePath.parse(CorporaConstants.SOURCE_AIP_CONTAINER, CorporaConstants.SOURCE_AIP_ID));

    // Retrieve, count and list AIP
    final IndexedAIP indexedAip = index.retrieve(IndexedAIP.class, aipId);
    compareAIPWithIndexedAIP(aip, indexedAip);

    final IndexResult<IndexedAIP> indexAips = index.find(IndexedAIP.class, null, null, new Sublist(0, 10), null);
    assertEquals(1, indexAips.getTotalCount());
    assertEquals(1, indexAips.getLimit());
    assertEquals(0, indexAips.getOffset());
    assertEquals(1, indexAips.getResults().size());

    final IndexedAIP aipFromList = indexAips.getResults().get(0);
    assertEquals(aip.getId(), aipFromList.getId());
    assertEquals(aip.isActive(), AIPState.ACTIVE.equals(aipFromList.getState()));
    assertEquals(aip.getParentId(), aipFromList.getParentID());

    assertEquals(indexedAip, aipFromList);
    assertEquals("fonds", indexedAip.getLevel());
    assertEquals("My example", indexedAip.getTitle());
    assertEquals("This is a very nice example", indexedAip.getDescription());
    assertEquals(RodaUtils.parseDate("0001-01-01T00:00:00.000+0000"), indexedAip.getDateInitial());
    assertEquals(RodaUtils.parseDate("0002-01-01T00:00:00.000+0000"), indexedAip.getDateFinal());

    // Retrieve, count and list SRO
    String rep1Id = aip.getRepresentations().get(0).getId();
    Representation rep1 = index.retrieve(Representation.class, IdUtils.getRepresentationId(aipId, rep1Id));
    assertEquals(rep1Id, rep1.getId());

    Filter filterParentTheAIP = new Filter();
    filterParentTheAIP.add(new SimpleFilterParameter(RodaConstants.REPRESENTATION_AIP_ID, aipId));
    IndexResult<Representation> sros = index.find(Representation.class, filterParentTheAIP, null, new Sublist(0, 10),
      null);
    assertEquals(aip.getRepresentations().size(), sros.getTotalCount());

    List<String> sro_IDs = new ArrayList<>();
    for (Representation sro : sros.getResults()) {
      sro_IDs.add(sro.getId());
    }

    List<String> representationIds = aip.getRepresentations().stream().map(r -> r.getId()).collect(Collectors.toList());
    assertThat(sro_IDs, Matchers.contains(representationIds.toArray()));

    /*
     * filterMimetype.add(new SimpleFilterParameter(RodaConstants.SRFM_MIMETYPE,
     * CorporaConstants.TEXT_XML));
     * assertEquals(index.find(SimpleEventPreservationMetadata.class,
     * filterMimetype, null, new Sublist(0, 10)).getTotalCount(),1L);
     */

    /*
     * SimpleRepresentationPreservationMetadata srpm =
     * index.retrieveSimpleRepresentationPreservationMetadata(aipId,
     * CorporaConstants.REPRESENTATION_1_ID,CorporaConstants.
     * REPRESENTATION_PREMIS_XML); assertEquals(srpm.getAipId(), aipId);
     * assertEquals(srpm.getFileId(),CorporaConstants.
     * REPRESENTATION_PREMIS_XML); Filter filterFileId = new Filter();
     * filterFileId.add(new SimpleFilterParameter(RodaConstants.SRPM_FILE_ID,
     * CorporaConstants.REPRESENTATION_PREMIS_XML));
     * assertEquals(""+index.countSimpleRepresentationPreservationMetadata(
     * filterFileId),""+1L);
     * assertEquals(index.find(SimpleEventPreservationMetadata.class,
     * filterFileId, null, new Sublist(0, 10)).getTotalCount(),1L);
     */
    model.deleteAIP(aipId);
    try {
      index.retrieve(IndexedAIP.class, aipId);
      fail("AIP deleted but yet it was retrieved");
    } catch (NotFoundException e) {
      // do nothing as it was the expected exception
    } catch (RODAException e) {
      fail("AIP was deleted and therefore a " + NotFoundException.class.getName()
        + " should have been thrown instead of a " + e.getClass().getName());
    }
  }

  @Test
  public void testAIPIndexCreateDelete2() throws RODAException {
    final String aipId = UUID.randomUUID().toString();

    model.createAIP(aipId, corporaService,
      DefaultStoragePath.parse(CorporaConstants.SOURCE_AIP_CONTAINER, CorporaConstants.SOURCE_AIP_ID_3));

    Filter filter = new Filter();
    filter.add(new SimpleFilterParameter(RodaConstants.AIP_ID, aipId));
    IndexedAIP indexedAip = index.find(IndexedAIP.class, filter, null, new Sublist(0, 10), null).getResults().get(0);
    Calendar calInitial = Calendar.getInstance();
    calInitial.setTime(indexedAip.getDateInitial());
    assertEquals(calInitial.get(Calendar.YEAR), CorporaConstants.YEAR_1213);
    Calendar calFinal = Calendar.getInstance();
    calFinal.setTime(indexedAip.getDateFinal());
    assertEquals(calFinal.get(Calendar.YEAR), CorporaConstants.YEAR_2003);

  }

  @Test
  public void testAIPUpdate() throws RODAException {
    // generate AIP ID
    final String aipId = UUID.randomUUID().toString();

    // testing AIP
    model.createAIP(aipId, corporaService,
      DefaultStoragePath.parse(CorporaConstants.SOURCE_AIP_CONTAINER, CorporaConstants.SOURCE_AIP_ID));

    final StoragePath otherAipPath = DefaultStoragePath.parse(CorporaConstants.SOURCE_AIP_CONTAINER,
      CorporaConstants.OTHER_AIP_ID);
    final AIP updatedAIP = model.updateAIP(aipId, corporaService, otherAipPath);

    final IndexedAIP indexedAIP = index.retrieve(IndexedAIP.class, aipId);

    compareAIPWithIndexedAIP(updatedAIP, indexedAIP);

    model.deleteAIP(aipId);
  }

  @Test
  public void testListCollections() throws RODAException {
    // set up
    model.createAIP(CorporaConstants.SOURCE_AIP_ID, corporaService,
      DefaultStoragePath.parse(CorporaConstants.SOURCE_AIP_CONTAINER, CorporaConstants.SOURCE_AIP_ID));
    model.createAIP(CorporaConstants.OTHER_AIP_ID, corporaService,
      DefaultStoragePath.parse(CorporaConstants.SOURCE_AIP_CONTAINER, CorporaConstants.OTHER_AIP_ID));

    long aipCount = index.count(IndexedAIP.class, IndexedAIP.FONDS_FILTER);
    assertEquals(1, aipCount);

    final IndexResult<IndexedAIP> aips = index.find(IndexedAIP.class, IndexedAIP.FONDS_FILTER, null,
      new Sublist(0, 10), null);

    assertEquals(1, aips.getLimit());
    assertEquals(CorporaConstants.SOURCE_AIP_ID, aips.getResults().get(0).getId());

    model.deleteAIP(CorporaConstants.SOURCE_AIP_ID);
    model.deleteAIP(CorporaConstants.OTHER_AIP_ID);
  }

  @Test
  public void testSubElements() throws RODAException {
    // set up
    model.createAIP(CorporaConstants.SOURCE_AIP_ID, corporaService,
      DefaultStoragePath.parse(CorporaConstants.SOURCE_AIP_CONTAINER, CorporaConstants.SOURCE_AIP_ID));
    model.createAIP(CorporaConstants.OTHER_AIP_ID, corporaService,
      DefaultStoragePath.parse(CorporaConstants.SOURCE_AIP_CONTAINER, CorporaConstants.OTHER_AIP_ID));

    Filter filter = new Filter();
    filter.add(new SimpleFilterParameter(RodaConstants.AIP_PARENT_ID, CorporaConstants.SOURCE_AIP_ID));

    long aipCount = index.count(IndexedAIP.class, filter);
    assertEquals(1, aipCount);

    final IndexResult<IndexedAIP> aips = index.find(IndexedAIP.class, filter, null, new Sublist(0, 10), null);

    assertEquals(1, aips.getLimit());
    assertEquals(CorporaConstants.OTHER_AIP_ID, aips.getResults().get(0).getId());

    model.deleteAIP(CorporaConstants.SOURCE_AIP_ID);
    model.deleteAIP(CorporaConstants.OTHER_AIP_ID);
  }

  @Test
  public void testGetAncestors() throws RODAException {
    // set up
    model.createAIP(CorporaConstants.SOURCE_AIP_ID, corporaService,
      DefaultStoragePath.parse(CorporaConstants.SOURCE_AIP_CONTAINER, CorporaConstants.SOURCE_AIP_ID));
    model.createAIP(CorporaConstants.OTHER_AIP_ID, corporaService,
      DefaultStoragePath.parse(CorporaConstants.SOURCE_AIP_CONTAINER, CorporaConstants.OTHER_AIP_ID));

    IndexedAIP aip = index.retrieve(IndexedAIP.class, CorporaConstants.OTHER_AIP_ID);
    List<IndexedAIP> ancestors = index.getAncestors(aip);
    assertThat(ancestors,
      Matchers.hasItem(Matchers.<IndexedAIP> hasProperty("id", Matchers.equalTo(CorporaConstants.SOURCE_AIP_ID))));
  }

  @Test
  public void testGetElementWithoutParentId() throws RODAException {
    // generate AIP ID
    final String aipId = UUID.randomUUID().toString();

    // Create AIP
    model.createAIP(aipId, corporaService,
      DefaultStoragePath.parse(CorporaConstants.SOURCE_AIP_CONTAINER, CorporaConstants.SOURCE_AIP_ID));

    Filter filter = new Filter();
    filter.add(new SimpleFilterParameter(RodaConstants.AIP_LEVEL, "fonds"));
    filter.add(new EmptyKeyFilterParameter(RodaConstants.AIP_PARENT_ID));
    IndexResult<IndexedAIP> findDescriptiveMetadata = index.find(IndexedAIP.class, filter, null, new Sublist(), null);

    assertNotNull(findDescriptiveMetadata);
    assertThat(findDescriptiveMetadata.getResults(), Matchers.hasSize(1));

    // cleanup
    model.deleteAIP(aipId);
  }

  @Test
  public void testGetLogEntriesCount() throws GenericException, RequestNotValidException, AuthorizationDeniedException,
    NotFoundException, InterruptedException {
    // cleaning up action log entries on index (if any)
    index.deleteAllActionLog();

    LogEntry entry = new LogEntry();
    entry.setActionComponent("Action");
    entry.setActionMethod("Method");
    entry.setAddress("Address");
    entry.setDatetime(new Date());
    entry.setDuration(10L);
    entry.setId("ID");
    entry.setRelatedObjectID("Related");
    entry.setUsername("Username");
    List<LogEntryParameter> parameters = new ArrayList<LogEntryParameter>();
    parameters.add(new LogEntryParameter("NAME1", "VALUE1"));
    parameters.add(new LogEntryParameter("NAME2", "VALUE2"));
    entry.setParameters(parameters);
    model.addLogEntry(entry, logPath);

    // wait for index soft commit
    Thread.sleep(6000);

    Filter filterDescription = new Filter();
    filterDescription.add(new SimpleFilterParameter(RodaConstants.LOG_ID, "ID"));
    assertThat(index.count(LogEntry.class, filterDescription), Matchers.is(1L));

    Filter filterDescription2 = new Filter();
    filterDescription2.add(new SimpleFilterParameter(RodaConstants.LOG_ID, "ID2"));
    assertThat(index.count(LogEntry.class, filterDescription2), Matchers.is(0L));
  }

  @Test
  public void testFindLogEntry() throws GenericException, RequestNotValidException, AuthorizationDeniedException,
    NotFoundException, InterruptedException {
    LogEntry entry = new LogEntry();
    entry.setActionComponent(RodaConstants.LOG_ACTION_COMPONENT);
    entry.setActionMethod("Method");
    entry.setAddress("address");
    entry.setDatetime(new Date());
    entry.setDuration(10L);
    entry.setId("id");
    entry.setRelatedObjectID("related");
    entry.setUsername("username");
    List<LogEntryParameter> parameters = new ArrayList<LogEntryParameter>();
    parameters.add(new LogEntryParameter("NAME1", "VALUE1"));
    parameters.add(new LogEntryParameter("NAME2", "VALUE2"));
    entry.setParameters(parameters);
    model.addLogEntry(entry, logPath);

    // wait for index soft commit
    Thread.sleep(6000);

    Filter filterDescription = new Filter();
    filterDescription.add(new SimpleFilterParameter(RodaConstants.LOG_ID, "id"));

    IndexResult<LogEntry> entries = index.find(LogEntry.class, filterDescription, null, new Sublist(), null);
    assertEquals(entries.getTotalCount(), 1);
    assertEquals(entries.getResults().get(0).getActionComponent(), RodaConstants.LOG_ACTION_COMPONENT);

    Filter filterDescription2 = new Filter();
    filterDescription2.add(new SimpleFilterParameter(RodaConstants.LOG_ID, "id2"));

    IndexResult<LogEntry> entries2 = index.find(LogEntry.class, filterDescription2, null, new Sublist(), null);
    assertEquals(entries2.getTotalCount(), 0);
  }

  @Test
  public void testReindexLogEntry() throws GenericException, RequestNotValidException, AuthorizationDeniedException,
    NotFoundException, InterruptedException {
    Long number = 10L;

    for (int i = 0; i < number; i++) {
      LogEntry entry = new LogEntry();
      entry.setId("ID" + i);
      entry.setActionComponent("ACTION:" + i);
      entry.setActionMethod("Method:" + i);
      entry.setAddress("ADDRESS");
      entry.setDatetime(new Date());
      entry.setDuration(i);
      entry.setRelatedObjectID("RELATED:" + i);
      entry.setUsername("USER:" + i);
      List<LogEntryParameter> parameters = new ArrayList<LogEntryParameter>();
      parameters.add(new LogEntryParameter("NAME1", "VALUE1"));
      parameters.add(new LogEntryParameter("NAME2", "VALUE2"));
      entry.setParameters(parameters);
      model.addLogEntry(entry, logPath, false);
    }

    // wait for index soft commit
    Thread.sleep(6000);

    model.findOldLogsAndMoveThemToStorage(logPath, null);
    index.reindexActionLogs();

    // wait for index soft commit
    Thread.sleep(6000);

    Filter f1 = new Filter();
    f1.add(new SimpleFilterParameter(RodaConstants.LOG_ACTION_COMPONENT, "ACTION:0"));
    IndexResult<LogEntry> entries1 = index.find(LogEntry.class, f1, null, new Sublist(0, 10), null);
    assertThat(entries1.getTotalCount(), Matchers.is(1L));
    Filter f2 = new Filter();
    f2.add(new SimpleFilterParameter(RodaConstants.LOG_ADDRESS, "ADDRESS"));
    IndexResult<LogEntry> entries2 = index.find(LogEntry.class, f2, null, new Sublist(0, 10), null);
    assertThat(entries2.getTotalCount(), Matchers.is(number));
  }

  @Test
  public void testReindexAIP() throws ParseException, RequestNotValidException, GenericException,
    AuthorizationDeniedException, AlreadyExistsException, NotFoundException, ValidationException {
    for (int i = 0; i < 10; i++) {
      final String aipId = UUID.randomUUID().toString();
      model.createAIP(aipId, corporaService,
        DefaultStoragePath.parse(CorporaConstants.SOURCE_AIP_CONTAINER, CorporaConstants.SOURCE_AIP_ID), false);
    }

    index.reindexAIPs();
    long count = index.count(IndexedAIP.class, new Filter());
    assertEquals(10L, count);

  }

  @Test
  public void indexMembers() throws AlreadyExistsException, GenericException, RequestNotValidException,
    EmailAlreadyExistsException, UserAlreadyExistsException, IllegalOperationException, NotFoundException {
    Set<String> groups = new HashSet<String>();
    groups.add("administrators");
    Set<String> roles = new HashSet<String>();
    roles.add("browse");

    for (int i = 0; i < 5; i++) {
      if (i % 2 == 0) {
        User user = new User();
        user.setActive(true);
        user.setAllGroups(groups);
        user.setAllRoles(roles);
        user.setDirectGroups(groups);
        user.setDirectRoles(roles);
        user.setEmail("mail@example.com");
        user.setGuest(false);
        user.setId("USER" + i);
        user.setName("NAMEUSER" + i);
        user.setFullName("NAMEUSER" + i);
        model.addUser(user, true, true);
      } else {
        Group group = new Group();
        group.setActive(true);
        group.setAllGroups(groups);
        group.setAllRoles(roles);
        group.setDirectGroups(groups);
        group.setDirectRoles(roles);
        group.setId("GROUP" + i);
        group.setName("NAMEGROUP" + i);
        group.setFullName("NAMEGROUP" + i);
        model.addGroup(group, true, true);
      }
    }

    Filter filterUSER1 = new Filter();
    filterUSER1.add(new SimpleFilterParameter(RodaConstants.MEMBERS_NAME, "NAMEUSER0"));
    filterUSER1.add(new SimpleFilterParameter(RodaConstants.MEMBERS_IS_USER, "true"));
    assertThat(index.count(RODAMember.class, filterUSER1), Matchers.is(1L));

    Filter filterGroup = new Filter();
    filterGroup.add(new SimpleFilterParameter(RodaConstants.MEMBERS_NAME, "NAMEGROUP1"));
    filterGroup.add(new SimpleFilterParameter(RodaConstants.MEMBERS_IS_USER, "false"));
    assertThat(index.count(RODAMember.class, filterGroup), Matchers.is(1L));

  }

  @Test
  public void testIdWithComma() throws RODAException {
    // generate AIP ID
    final String origAipId = "id, with, comma";
    final String aipId = UUID.nameUUIDFromBytes(origAipId.getBytes()).toString();

    // Create AIP
    model.createAIP(aipId, corporaService,
      DefaultStoragePath.parse(CorporaConstants.SOURCE_AIP_CONTAINER, CorporaConstants.SOURCE_AIP_ID));

    IndexResult<IndexedAIP> find = index.find(IndexedAIP.class, null, null, new Sublist(0, 10));
    assertEquals(1, find.getTotalCount());

    IndexedAIP aip = index.retrieve(IndexedAIP.class, UUID.nameUUIDFromBytes(origAipId.getBytes()).toString());
    assertNotNull(aip);

    // cleanup
    model.deleteAIP(aipId);
  }

  @Test
  public void testRiskIndex() {
    try {
      Risk risk = new Risk();
      risk.setId("R1");
      risk.setName("Risk name");
      risk.setDescription("Risk description");
      risk.setIdentifiedOn(new Date());
      risk.setIdentifiedBy("Risk identifier");
      risk.setCategory("Risk category");
      risk.setNotes("Risk notes");

      risk.setPreMitigationProbability(1);
      risk.setPreMitigationImpact(1);
      risk.setPreMitigationSeverity(1);
      risk.setPreMitigationNotes("Pre Notes");

      risk.setPosMitigationProbability(2);
      risk.setPosMitigationImpact(2);
      risk.setPosMitigationSeverity(2);
      risk.setPosMitigationNotes("Pos Notes");

      risk.setMitigationStrategy("Mitigation Strategy");
      risk.setMitigationOwnerType("Owner type");
      risk.setMitigationOwner("Owner");
      risk.setMitigationRelatedEventIdentifierType("Mitigation REI type");
      risk.setMitigationRelatedEventIdentifierValue("Mitigation REI value");

      HashMap<String, String> affectedObjects = new HashMap<String, String>();
      affectedObjects.put("Affected related type", "Affected related value");
      risk.setAffectedObjects(affectedObjects);
      model.createRisk(risk, true);

      Risk risk2 = model.retrieveRisk("R1");
      assertNotNull(risk2);
      assertEquals(risk.getId(), risk2.getId());
      assertEquals(risk.getName(), risk2.getName());

      IndexResult<Risk> find = index.find(Risk.class, null, null, new Sublist(0, 10));
      assertEquals(1, find.getTotalCount());

      Risk risk3 = index.retrieve(Risk.class, "R1");
      assertNotNull(risk3);
      assertEquals(risk.getId(), risk3.getId());
      assertEquals(risk.getName(), risk3.getName());

      risk3.setName("Risk New Name");
      model.updateRisk(risk3, true);

      IndexResult<Risk> find2 = index.find(Risk.class, null, null, new Sublist(0, 10));
      assertEquals(1, find2.getTotalCount());

      Risk risk4 = index.retrieve(Risk.class, "R1");
      assertNotNull(risk4);
      assertEquals(risk.getId(), risk4.getId());

      model.deleteRisk("R1");

    } catch (GenericException | RequestNotValidException | NotFoundException | AuthorizationDeniedException e) {
      assertTrue(false);
    }
  }
}
