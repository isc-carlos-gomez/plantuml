package net.sourceforge.plantuml.communicationdiagram;

import java.util.UUID;

import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.cucadiagram.LinkType;

/**
 * A {@link Link} for a Communication Diagram.
 *
 * @author Carlos Gomez
 */
class CommunicationLink extends Link {

  private final UUID groupId;

  /**
   * Creates a new instance.
   *
   * @param template
   *        regular link to use as a template for the properties of this new link
   * @param groupId
   *        ID of the link group this link will belong to
   * @param type
   *        link type
   * @param minLength
   *        parameter used to calculate the minimum link length
   * @param arrowStyle
   *        style (dashed, bold, dotted, etc) of the arrows of this link
   */
  CommunicationLink(final Link template, final UUID groupId, final LinkType type, final int minLength,
      final String arrowStyle) {
    this(template, groupId, type, minLength, template.getLabel());
    applyStyle(arrowStyle);
  }

  private CommunicationLink(final Link delegate, final UUID groupId, final LinkType type, final int length,
      final Display label) {
    super(
        delegate.getEntity1(),
        delegate.getEntity2(),
        type,
        label,
        length,
        delegate.getQualifier1(),
        delegate.getQualifier2(),
        delegate.getLabeldistance(),
        delegate.getLabelangle(),
        delegate.getSpecificColor(),
        delegate.getStyleBuilder(),
        delegate.isInverted());

    setUrl(delegate.getUrl());
    setPortMembers(
        delegate.getEntity1().getIdent().getPortMember(),
        delegate.getEntity2().getIdent().getPortMember());

    setLinkArrow(delegate.getLinkArrow());
    setColors(delegate.getColors());

    this.groupId = groupId;
  }

  /**
   * @return the ID of the link group this link belongs to
   */
  @Override
  public UUID getGroupId() {
    return this.groupId;
  }

  /**
   * @param label
   *        label that will be appended to this link
   * @return a clone of this link with the given label appended to the original label
   */
  CommunicationLink withAppendedLabel(final Display label) {
    final Display composedLabel = getLabel().addAll(label);
    return withLabel(composedLabel);
  }

  /**
   * @param label
   *        label for the new link
   * @return a clone of this link with the given label replacing the original label
   */
  CommunicationLink withLabel(final Display newLabel) {
    return new CommunicationLink(this, this.groupId, getType(), getLength(), newLabel);
  }

  /**
   * @param link
   *        the link to compare
   * @return whether this link has the same ends (aka. entities) as the given link
   */
  boolean hasSameEnds(final Link link) {
    return getEntity1().equals(link.getEntity1())
        && getEntity2().equals(link.getEntity2());
  }

  /**
   * @param link
   *        the link to compare
   * @return whether this link has ends (aka. entities) opposite to the given link
   */
  boolean hasOppositeEnds(final Link link) {
    return getEntity1().equals(link.getEntity2())
        && getEntity2().equals(link.getEntity1());
  }

}
