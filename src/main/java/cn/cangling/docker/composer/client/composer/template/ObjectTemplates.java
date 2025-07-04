package cn.cangling.docker.composer.client.composer.template;

import cn.cangling.docker.composer.client.composer.model.Images;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ObjectTemplates {
    public static final String DATA_TYPE = "application/x-my-app-object";
    public static ObjectTemplates INSTANCE;
    public static ObjectTemplates get() {
        if (INSTANCE == null) {
            INSTANCE = new ObjectTemplates();
            INSTANCE.init();
        }
        return INSTANCE;
    }
    private List<ObjectTemplate> list;
    private void init(){
        list = new ArrayList<ObjectTemplate>();
        list.add(ObjectTemplate.createService(Images.getMqtt(),"MQTT"));
        list.add(ObjectTemplate.createService(Images.getRedis(),"Redis"));
        list.add(ObjectTemplate.createService(Images.getPostgres(),"PostGIS"));
        list.add(ObjectTemplate.createService(Images.getKafka(),"Kafka"));
        list.add(ObjectTemplate.createService(Images.getGeoserver(),"GeoServer"));
        list.add(ObjectTemplate.createNetwork(Images.getNet(),"Network"));
        list.add(ObjectTemplate.createVolume(Images.getHard(),"Volume"));
        list.add(ObjectTemplate.createVolume(Images.getNginx(),"Nginx"));
    }
    public ObjectTemplate findByName(String name){
        for(ObjectTemplate objectTemplate : list){
            if (objectTemplate.name.equals(name)) {
                return objectTemplate;
            }
        }
        return null;
    }
    public void each(Consumer<ObjectTemplate> consumer){
        if(consumer == null) return;
        for(ObjectTemplate objectTemplate : list){
            consumer.accept(objectTemplate);
        }
    }
}
