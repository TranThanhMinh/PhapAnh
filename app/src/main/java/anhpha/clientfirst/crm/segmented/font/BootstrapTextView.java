package anhpha.clientfirst.crm.segmented.font;

import android.support.annotation.Nullable;

import anhpha.clientfirst.crm.segmented.utils.BootstrapText;


/**
 * Views which implement this interfaces can set their text using BootstrapText
 */
public interface BootstrapTextView {

    /**
     * Sets the view to display the given BootstrapText
     *
     * @param bootstrapText the BootstrapText
     */
    void setBootstrapText(@Nullable BootstrapText bootstrapText);

    /**
     * @return the current BootstrapText, or null if none exists
     */
    @Nullable BootstrapText getBootstrapText();

    /**
     * Sets the view to display the given markdown text, by constructing a BootstrapText. e.g.
     * "This is a {fa-stop} button"
     *
     * @param text the markdown text
     */
    void setMarkdownText(@Nullable String text);

}
