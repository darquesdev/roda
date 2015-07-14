/**
 * 
 */
package pt.gov.dgarq.roda.wui.dissemination.client;

import pt.gov.dgarq.roda.core.data.eadc.DescriptionLevel;
import pt.gov.dgarq.roda.core.data.eadc.DescriptionLevelInfo;
import pt.gov.dgarq.roda.wui.common.client.BadHistoryTokenException;
import pt.gov.dgarq.roda.wui.common.client.HistoryResolver;
import pt.gov.dgarq.roda.wui.common.client.UserLogin;
import pt.gov.dgarq.roda.wui.common.client.tools.Tools;
import pt.gov.dgarq.roda.wui.common.client.widgets.HTMLWidgetWrapper;
import pt.gov.dgarq.roda.wui.dissemination.browse.client.Browse;
import pt.gov.dgarq.roda.wui.dissemination.search.client.Search;
import pt.gov.dgarq.roda.wui.main.client.Main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import config.i18n.client.CommonConstants;
import config.i18n.client.DisseminationConstants;

/**
 * @author Luis Faria
 * 
 */
public class Dissemination implements HistoryResolver {

	private static CommonConstants commonConstants = (CommonConstants) GWT.create(CommonConstants.class);
	private static DisseminationConstants constants = (DisseminationConstants) GWT.create(DisseminationConstants.class);

	private static Dissemination instance = null;

	/**
	 * Get the singleton instance
	 * 
	 * @return the instance
	 */
	public static Dissemination getInstance() {
		if (instance == null) {
			instance = new Dissemination();
		}
		return instance;
	}

	// private GWTLogger logger = new GWTLogger(GWT.getTypeName(this));

	private boolean initialized;

	private HTMLWidgetWrapper page;

	private HTMLWidgetWrapper help = null;

	private Dissemination() {
		initialized = false;

	}

	private void init() {
		if (!initialized) {
			initialized = true;
			page = new HTMLWidgetWrapper("Dissemination.html");
			page.addStyleName("wui-dissemination");
		}
	}

	private HTMLWidgetWrapper getHelp() {
		if (help == null) {
			help = new HTMLWidgetWrapper("DisseminationHelp.html");
		}
		return help;
	}

	public void isCurrentUserPermitted(AsyncCallback<Boolean> callback) {
		UserLogin.getInstance().checkRoles(new HistoryResolver[] { Search.getInstance(), Browse.getInstance() }, false,
				callback);
	}

	public void resolve(String[] historyTokens, AsyncCallback<Widget> callback) {
		if (historyTokens.length == 0) {
			init();
			callback.onSuccess(page);
		} else {
			if (historyTokens[0].equals(Search.getInstance().getHistoryToken())) {
				Search.getInstance().resolve(Tools.tail(historyTokens), callback);

			} else if (historyTokens[0].equals(Browse.getInstance().getHistoryToken())) {
				Browse.getInstance().resolve(Tools.tail(historyTokens), callback);

			} else if (historyTokens[0].equals("help")) {
				callback.onSuccess(getHelp());

			} else {
				callback.onFailure(new BadHistoryTokenException(historyTokens[0]));
			}
		}
	}

	public String getHistoryPath() {
		return getHistoryToken();
	}

	public String getHistoryToken() {
		return "dissemination";
	}

	/**
	 * Get description level icon
	 * 
	 * @param level
	 * @return the icon message
	 */
	public Image getElementLevelIcon(String level) {
		Image ret;
		final DescriptionLevelInfo levelInfo = Main.getDescriptionLevel(level);
		if (levelInfo != null) {
			ret = new Image(
					GWT.getModuleBaseURL() + "description_levels/" + levelInfo.getCategory().getCategory() + ".png");
			ret.setAltText(levelInfo.getLabel(commonConstants.locale()));
		} else {
			ret = new Image(GWT.getModuleBaseURL() + "description_levels/default.png");
			ret.setAltText("default");
		}

		if (Main.DESCRIPTION_LEVELS.contains(level)) {

		} else {
			ret = null;
		}
		return ret;
	}

	/**
	 * Get translation of each descriptive level
	 * 
	 * @param level
	 * @return the translation string
	 */
	public String getElementLevelTranslation(DescriptionLevel level) {
		String ret;
		if (Main.DESCRIPTION_LEVELS.contains(level)) {
			ret = constants.getString(level.getLevelSanitized());
		} else {
			ret = null;
		}
		return ret;
	}
}
