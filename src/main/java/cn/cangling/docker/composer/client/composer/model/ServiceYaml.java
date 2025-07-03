package cn.cangling.docker.composer.client.composer.model;

import elemental2.core.JsArray;
import jsinterop.annotations.JsType;


@JsType
public class ServiceYaml {

    public JsArray<Service> services = new JsArray<>();
    public JsArray<Network> networks = new JsArray<>();
    public JsArray<Volume> volumes = new JsArray<>();

}
