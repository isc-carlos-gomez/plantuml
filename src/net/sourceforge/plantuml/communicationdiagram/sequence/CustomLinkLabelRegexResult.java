package net.sourceforge.plantuml.communicationdiagram.sequence;

import java.util.HashMap;

import net.sourceforge.plantuml.command.regex.RegexPartialMatch;
import net.sourceforge.plantuml.command.regex.RegexResult;

/**
 * {@link RegexResult} decorator to overwrite link labels.
 *
 * @author Carlos Gomez
 */
class CustomLinkLabelRegexResult extends RegexResult {

  private static final String LINK_LABEL_KEY = "LABEL_LINK";
  private final RegexResult delegate;
  private final String customLinkLabel;

  /**
   * Creates a new instance.
   *
   * @param originalResult
   *        the original result to decorate with a new link label
   * @param customLinkLabel
   *        the new link label
   */
  CustomLinkLabelRegexResult(final RegexResult originalResult, final String customLinkLabel) {
    super(new HashMap<>());
    this.delegate = originalResult;
    this.customLinkLabel = customLinkLabel;
  }

  @Override
  public String get(final String key, final int num) {
    if (LINK_LABEL_KEY.equals(key)) {
      return this.customLinkLabel;
    }
    return this.delegate.get(key, num);
  }

  @Override
  public RegexPartialMatch get(final String key) {
    return this.delegate.get(key);
  }

  @Override
  public String getLazzy(final String key, final int num) {
    return this.delegate.getLazzy(key, num);
  }

  @Override
  public int size() {
    return this.delegate.size();
  }

  @Override
  public String toString() {
    return this.delegate.toString();
  }

}
