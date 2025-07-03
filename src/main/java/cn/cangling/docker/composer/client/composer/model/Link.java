package cn.cangling.docker.composer.client.composer.model;


public class Link{
    public RendingObject start;
    public RendingObject end;
    public LinkKind kind;

    public Link(RendingObject start, RendingObject end) {
        this.start = start;
        this.end = end;
        kind=LinkKind.LINK_KIND_UNKNOWN;
    }
    public void setLinkKind(LinkKind kind)
    {
        this.kind = kind;
    }
}
