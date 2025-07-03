package cn.cangling.docker.composer.client.composer.template;

import elemental2.dom.HTMLImageElement;

public class ObjectTemplate {
   public ObjectKind kind;
    public HTMLImageElement image;
    public String name;

    public static ObjectTemplate create(ObjectKind kind,HTMLImageElement image, String name) {
        ObjectTemplate objectTemplate = new ObjectTemplate();
        objectTemplate.kind = kind;
        objectTemplate.image = image;
        objectTemplate.name = name;
        return objectTemplate;
    }
    public static ObjectTemplate createService(HTMLImageElement image, String name) {
        return create(ObjectKind.SERVICE, image, name);
    }
    public static ObjectTemplate createNetwork(HTMLImageElement image, String name) {
        return create(ObjectKind.NETWORK, image, name);
    }
    public static ObjectTemplate createVolume(HTMLImageElement image, String name) {
        return create(ObjectKind.VOLUME, image, name);
    }
}
