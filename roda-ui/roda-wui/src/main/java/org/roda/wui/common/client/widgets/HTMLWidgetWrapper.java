/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE file at the root of the source
 * tree and available online at
 *
 * https://github.com/keeps/roda
 */
/**
 * 
 */
package org.roda.wui.common.client.widgets;

import org.roda.core.data.exceptions.GenericException;
import org.roda.wui.client.main.Theme;
import org.roda.wui.common.client.ClientLogger;
import org.roda.wui.common.client.tools.HistoryUtils;
import org.roda.wui.common.client.tools.RestUtils;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;

/**
 * @author Luís Faria
 * 
 */
public class HTMLWidgetWrapper extends HTML {

  private ClientLogger logger = new ClientLogger(getClass().getName());

  public HTMLWidgetWrapper(String resourceId) {
    this(resourceId, new AsyncCallback<Void>() {

      @Override
      public void onFailure(Throwable caught) {
        // do nothing
      }

      @Override
      public void onSuccess(Void result) {
        // do nothing
      }
    });
  }

  public HTMLWidgetWrapper(String resourceId, final AsyncCallback<Void> callback) {
    String id = resourceId;
    boolean isMarkdown = false;
    if (id.endsWith(".html")) {
      id = id.substring(0, id.length() - 5);
    }
    if (id.endsWith(".md")) {
      isMarkdown = true;
      id = id.substring(0, id.length() - 3);
    }

    String locale = LocaleInfo.getCurrentLocale().getLocaleName();

    String localizedResourceId;
    String defaultResourceId;
    if (isMarkdown) {
      localizedResourceId = id + "_" + locale + ".md";
      defaultResourceId = id + ".md";
    } else {
      localizedResourceId = id + "_" + locale + ".html";
      defaultResourceId = id + ".html";
    }

    RequestBuilder request = new RequestBuilder(RequestBuilder.GET,
      RestUtils.createThemeResourceUri(localizedResourceId, defaultResourceId, false).asString());

    final boolean transformMarkdownIntoHTML = isMarkdown;
    try {
      request.sendRequest(null, new RequestCallback() {

        @Override
        public void onResponseReceived(Request request, Response response) {
          if (response.getStatusCode() == 200) {
            String html;
            if (transformMarkdownIntoHTML) {
              html = markdownToHtml(response.getText());

              // work around the URL-encode on the $ character
              String filenameToken = "replace-with-capture-group";

              // fix links to other markdown files by replacing them with
              // proper "#theme/*.md" links
              RegExp mdRegExp = RegExp.compile("<a href=\"(?:(?![a-zA-Z]+:\\/\\/))(?:(?![#/]))(.*?\\.md)\">", "g");
              String mdReplacement = ("<a href=\"" + HistoryUtils.createHistoryHashLink(Theme.RESOLVER, filenameToken)
                + "\">").replace(filenameToken, "$1");

              html = mdRegExp.replace(html, mdReplacement);

              // <img src="images/kitematic_search.png" alt="Search and install"
              // title="Search and install RODA in Kitematic">

              // fix image links by replacing them with proper
              // "#theme/images/..." links
              RegExp imgRegExp = RegExp.compile("<img src=\"(images/.*?)\"", "g");
              String imgReplacement = ("<img src=\""
                + RestUtils.createThemeResourceUri(filenameToken, null, false).asString() + "\"").replace(filenameToken,
                  "$1");

              html = imgRegExp.replace(html, imgReplacement);
            } else {
              html = response.getText();
            }
            HTMLWidgetWrapper.this.setHTML(html);
            callback.onSuccess(null);
          } else if (response.getStatusCode() == 404) {
            if (transformMarkdownIntoHTML) {
              HistoryUtils.newHistory(Theme.RESOLVER, "Error404.md");
            } else {
              HistoryUtils.newHistory(Theme.RESOLVER, "Error404.html");
            }
          } else {
            logger.error("Error sending request");
            callback.onFailure(new GenericException("Error sending request"));
          }
        }

        @Override
        public void onError(Request request, Throwable exception) {
          logger.error("Error sending request", exception);
          callback.onFailure(exception);
        }
      });
    } catch (RequestException exception) {
      logger.error("Error sending request", exception);
    }
  }

  private static native String markdownToHtml(String markdownText) /*-{
                                                                   var conv = new $wnd.showdown.Converter({flavor: 'github'});
                                                                   return "<div class=\"static-page max-width markdown\">" + conv.makeHtml(markdownText) + "</div>";
                                                                   }-*/;

  public void onCompletion(String responseText) {
    this.setHTML(responseText);
  }

}
