package cn.cangling.docker.composer.client.composer.model;

import elemental2.core.JsArray;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsType;

@JsType
public class Service {
    public String name;
    public String image;
    public JsArray<String> networks;
    public JsArray<String> volumes;
    public JsArray<String> command;
    public JsArray<String> entrypoint;
    public JsArray<String> environment;
    public JsArray<String> labels;
    //对外端口 innerport<:outerport>
    public JsArray<String> ports;
    public String user;
    public HealthChecker healthcheck;
    public JsArray<String> dependsOn;

    public DesignData designData;

    @JsConstructor
    public Service(String name) {
        this.name = name;
        designData=new DesignData();
        networks = new JsArray<>();
        volumes = new JsArray<>();
        command = new JsArray<>();
        entrypoint = new JsArray<>();
        environment = new JsArray<>();
        labels = new JsArray<>();
        ports = new JsArray<>();
        user = "";
        healthcheck = new HealthChecker();
        dependsOn = new JsArray<>();
    }

    public boolean addNetwork(String name) {
        if (name == null || name.length() == 0) {
            return false;
        }
        if (networks.includes(name)) return false;
        networks.push(name);
        return true;
    }

    public boolean addVolume(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        if (volumes.includes(name)) return false;
        volumes.push(name);
        return true;
    }

    public boolean addDependency(String serviceName) {
        if (serviceName == null || serviceName.isEmpty()) {
            return false;
        }
        if (dependsOn.includes(serviceName)) return false;
        dependsOn.push(serviceName);
        return true;
    }
}
