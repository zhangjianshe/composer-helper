package cn.cangling.docker.composer.client.composer.model;

import cn.cangling.docker.composer.client.composer.event.GraphEvent;
import cn.cangling.docker.composer.client.composer.event.GraphEventHandler;
import cn.cangling.docker.composer.client.composer.event.HasGraphEventHandler;
import cn.cangling.docker.composer.client.js.Jss;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import elemental2.core.Global;
import elemental2.dom.CanvasRenderingContext2D;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class YamlGraph implements HasGraphEventHandler {
    private final List<ServiceObject> serviceObjects = new ArrayList<>();
    private final List<NetworkObject> networksObjects = new ArrayList<>();
    private final List<VolumeObject> volumeObjects = new ArrayList<>();
    private final List<LinkObject> linkObjects = new ArrayList<>();
    private final List<RendingObject> actionObjects = new ArrayList<>();
    private final DragBoxObject dragBoxObject = new DragBoxObject();
    boolean enableDrag = false;
    List<RendingObject> selectedObjects = new ArrayList<>();
    List<RendingObject> highlights = new ArrayList<>();
    SimpleEventBus eventBus = new SimpleEventBus();

    public DragBoxObject enableDragBox(boolean enable) {
        enableDrag = enable;
        return dragBoxObject;
    }

    public ServiceObject addService(Service service) {
        ServiceObject serviceObject = new ServiceObject(service);
        serviceObject.setGraph(this);
        serviceObjects.add(serviceObject);
        return serviceObject;
    }

    public NetworkObject addNetwork(Network network) {
        NetworkObject networkObject = new NetworkObject(network);
        networkObject.setGraph(this);
        networkObject.getRect().set(network.designData.x, network.designData.y);
        networksObjects.add(networkObject);
        return networkObject;
    }

    public String nextVolumeName(String namePrefix) {
        int startIndex = volumeObjects.size() + 1;

        String name = namePrefix + startIndex;
        while (true) {
            boolean find = false;
            for (VolumeObject volumeObject : volumeObjects) {
                if (volumeObject.getRefObject().name.equals(name)) {
                    find = true;
                    break;
                }
            }
            if (find) {
                startIndex++;
                name = namePrefix + startIndex;
            } else {
                break;
            }
        }
        return name;
    }

    public String nextServiceName(String namePrefix) {
        int startIndex = serviceObjects.size() + 1;

        String name = namePrefix + startIndex;
        while (true) {
            boolean find = false;
            for (ServiceObject serviceObject : serviceObjects) {
                if (serviceObject.getRefObject().name.equals(name)) {
                    find = true;
                    break;
                }
            }
            if (find) {
                startIndex++;
                name = namePrefix + startIndex;
            } else {
                break;
            }
        }
        return name;
    }

    public String nextNetworkName(String namePrefix) {
        int startIndex = networksObjects.size() + 1;

        String name = namePrefix + startIndex;
        while (true) {
            boolean find = false;
            for (NetworkObject networkObject : networksObjects) {
                if (networkObject.getRefObject().name.equals(name)) {
                    find = true;
                    break;
                }
            }
            if (find) {
                startIndex++;
                name = namePrefix + startIndex;
            } else {
                break;
            }
        }
        return name;
    }

    public VolumeObject addVolume(Volume volume) {
        VolumeObject volumeObject = new VolumeObject(volume);
        volumeObject.getRect().set(volume.designData.x, volume.designData.y);
        volumeObject.setGraph(this);
        volumeObjects.add(volumeObject);
        return volumeObject;
    }

    public LinkObject addLink(Link link) {
        LinkObject linkObject = new LinkObject(link);
        linkObject.setGraph(this);
        linkObjects.add(linkObject);
        return linkObject;
    }

    public void build() {
        linkObjects.clear();
        for (ServiceObject serviceObject : serviceObjects) {
            Service service = serviceObject.getRefObject();
            if (service == null) {
                continue;
            }
            for (String networkName : service.networks.asList()) {
                NetworkObject networkObject = findNetworkObject(networkName);
                if (networkObject == null) {
                    continue;
                }
                addLink(new Link(serviceObject, networkObject));
            }
            for (String volumeName : service.volumes.asList()) {
                VolumeObject volumeObject = findVolumeObject(volumeName);
                if (volumeObject == null) {
                    continue;
                }
                addLink(new Link(serviceObject, volumeObject));
            }
        }
        //加载依赖关系
        for (ServiceObject serviceObject : serviceObjects) {
            Service service = serviceObject.getRefObject();
            //update position information
            DesignData data = service.designData;
            serviceObject.getRect().set(data.x, data.y);

            for (String dependency : service.dependsOn.asList()) {
                ServiceObject serviceObject1 = findServiceObject(dependency);
                if (serviceObject1 == null) {
                    continue;
                }
                Link link = new Link(serviceObject, serviceObject1);
                link.setLinkKind(LinkKind.LINK_KIND_DEPENDENCY);
                addLink(link);
            }
        }
    }

    public NetworkObject findNetworkObject(String networkName) {
        for (NetworkObject networkObject : networksObjects) {
            if (networkObject.getRefObject().name.equals(networkName)) {
                return networkObject;
            }
        }
        return null;
    }

    public VolumeObject findVolumeObject(String volumeName) {
        for (VolumeObject volumeObject : volumeObjects) {
            if (volumeObject.getRefObject().name.equals(volumeName)) {
                return volumeObject;
            }
        }
        return null;
    }

    public ServiceObject findServiceObject(String serviceName) {
        for (ServiceObject serviceObject : serviceObjects) {
            if (serviceObject.getRefObject().name.equals(serviceName)) {
                return serviceObject;
            }
        }
        return null;
    }

    public void draw(CanvasRenderingContext2D context2D, double t) {
        for (LinkObject linkObject : linkObjects) {
            linkObject.draw(context2D, t);
        }
        for (ServiceObject serviceObject : serviceObjects) {
            serviceObject.draw(context2D, t);
        }
        for (NetworkObject networkObject : networksObjects) {
            networkObject.draw(context2D, t);
        }
        for (VolumeObject volumeObject : volumeObjects) {
            volumeObject.draw(context2D, t);
        }


        for (RendingObject actionObject : actionObjects) {
            actionObject.draw(context2D, t);
        }
        if (enableDrag) {
            dragBoxObject.draw(context2D, t);
        }
    }

    public void clearSelected() {
        for (RendingObject object : selectedObjects) {
            object.setSelected(false);
        }
        selectedObjects.clear();
        fireEvent(GraphEvent.selectChangeEvent(this));
    }

    public void appendSelectObject(RendingObject object, boolean fireEvent) {
        if (object == null) return;
        object.setSelected(true);
        selectedObjects.add(object);
        if (fireEvent) {
            fireEvent(GraphEvent.selectChangeEvent(this));
        }
    }

    public void removeSelectObject(RendingObject object, boolean fireEvent) {
        if (object == null) return;
        selectedObjects.remove(object);
        object.setSelected(false);
        if (fireEvent) {
            fireEvent(GraphEvent.selectChangeEvent(this));
        }
    }

    public void appendSelectObjects(boolean clear, List<RendingObject> highlight) {
        if (clear) {
            clearSelected();
        }
        for (RendingObject object : highlight) {
            appendSelectObject(object, false);
        }
        fireEvent(GraphEvent.selectChangeEvent(this));
    }

    public HitTestResult hitTest(Point ptCanvasPoint) {
        for (RendingObject object : serviceObjects) {
            HitTestResult hitTest = object.hitTest(ptCanvasPoint.x, ptCanvasPoint.y);
            if (hitTest.getHitTest() != HitTest.HT_NONE) {
                return hitTest;
            }
        }
        for (RendingObject object : volumeObjects) {
            HitTestResult hitTest = object.hitTest(ptCanvasPoint.x, ptCanvasPoint.y);
            if (hitTest.getHitTest() != HitTest.HT_NONE) {
                return hitTest;
            }
        }
        for (RendingObject object : networksObjects) {
            HitTestResult hitTest = object.hitTest(ptCanvasPoint.x, ptCanvasPoint.y);
            if (hitTest.getHitTest() != HitTest.HT_NONE) {
                return hitTest;
            }
        }
        return HitTestResult.create(HitTest.HT_NONE, null);
    }

    /**
     * 查找从sourceObject出发的连接
     *
     * @param sourceObject
     * @return
     */
    public List<LinkObject> findSourceLinkObject(RendingObject sourceObject) {
        List<LinkObject> list = new ArrayList<>();
        for (LinkObject linkObject : linkObjects) {
            if (linkObject.getRefObject().start.equals(sourceObject)) {
                list.add(linkObject);
            }
        }
        return list;
    }

    /**
     * 查找从sourceObject出发的连接
     *
     * @param targetObject
     * @return
     */
    public List<LinkObject> findTargetLinkObject(RendingObject targetObject) {
        List<LinkObject> list = new ArrayList<>();
        for (LinkObject linkObject : linkObjects) {
            if (linkObject.getRefObject().end.equals(targetObject)) {
                list.add(linkObject);
            }
        }
        return list;
    }

    public void offsetSelected(double dx, double dy) {
        if (selectedObjects.size() > 0) {
            for (RendingObject object : selectedObjects) {
                object.offset(dx, dy);
            }
        }
    }

    public void addActionObject(RendingObject actionObject) {
        actionObjects.add(actionObject);
    }

    public void removeActionObject(RendingObject actionObject) {
        actionObjects.remove(actionObject);
    }

    public List<RendingObject> getSelectObjectList() {
        return selectedObjects;
    }

    /**
     * 高亮框选的内容
     *
     * @param rect
     * @return
     */
    public List<RendingObject> highlight(Rect rect) {
        clearHighlight();
        for (ServiceObject serviceObject : serviceObjects) {
            if (serviceObject.intersect(rect)) {
                serviceObject.setHilight(true);
                highlights.add(serviceObject);
            }
        }
        for (VolumeObject volumeObject : volumeObjects) {
            if (volumeObject.intersect(rect)) {
                volumeObject.setHilight(true);
                highlights.add(volumeObject);
            }
        }
        for (NetworkObject networkObject : networksObjects) {
            if (networkObject.intersect(rect)) {
                highlights.add(networkObject);
            }
        }
        return highlights;
    }

    public void clearHighlight() {
        for (RendingObject object : highlights) {
            object.setHilight(false);
        }
        highlights.clear();
    }

    /**
     * canvasX,canvasY 是否在选中的范围内
     *
     * @param canvasX
     * @param canvasY
     * @return
     */
    public boolean pointInSelection(double canvasX, double canvasY) {
        for (RendingObject object : selectedObjects) {
            if (object.getDrawingRect().contains(canvasX, canvasY)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 自动布局
     * 首先按照服务的依赖关系 将服务进行分层管理
     * 将网络平铺
     * 将磁盘平铺
     */
    public void autoLayout() {

    }

    /**
     * 导出到XML文件中
     *
     * @return
     */
    public String exportToJson() {
        ServiceYaml yaml = new ServiceYaml();
        for (ServiceObject serviceObject : serviceObjects) {
            Service service = serviceObject.getRefObject();
            service.designData.setData(serviceObject.getDrawingRect().x, serviceObject.getDrawingRect().y);
            yaml.services.push(service);
        }
        for (NetworkObject networkObject : networksObjects) {
            Network network = networkObject.getRefObject();
            network.designData.setData(networkObject.getDrawingRect().x, networkObject.getDrawingRect().y);
            yaml.networks.push(network);
        }
        for (VolumeObject volumeObject : volumeObjects) {
            Volume volume = volumeObject.getRefObject();
            volume.designData.setData(volumeObject.getDrawingRect().x, volumeObject.getDrawingRect().y);
            yaml.volumes.push(volume);
        }
        return Global.JSON.stringify(yaml);
    }

    /**
     * 该方法会将原声的 javascript object转化为 Java Type
     *
     * @param tempGraph
     */
    public void loadFromJson(String tempGraph) {
        if (tempGraph == null || tempGraph.isEmpty()) {
            clear();
        } else {
            Object obj = Global.JSON.parse(tempGraph);
            ServiceYaml yaml = Jss.castTo(obj, ServiceYaml.class);
            for (int i = 0; i < yaml.services.length; i++) {
                Service service = Jss.castTo(yaml.services.getAt(i), Service.class);
                Jss.castTo(service.designData, DesignData.class);
                Jss.castTo(service.healthcheck, HealthChecker.class);

                addService(service);
            }
            for (int i = 0; i < yaml.networks.length; i++) {
                Network network = Jss.castTo(yaml.networks.getAt(i), Network.class);
                Jss.castTo(network.designData, DesignData.class);
                addNetwork(network);
            }
            for (int i = 0; i < yaml.volumes.length; i++) {
                Volume volume = Jss.castTo(yaml.volumes.getAt(i), Volume.class);
                Jss.castTo(volume.designData, DesignData.class);
                addVolume(volume);
            }
            build();


        }
    }

    /**
     * 清空图
     */
    public void clear() {
        clearHighlight();
        clearSelected();
        linkObjects.clear();
        serviceObjects.clear();
        volumeObjects.clear();
        networksObjects.clear();
        actionObjects.clear();
    }

    @Override
    public HandlerRegistration addGraphEventHandler(GraphEventHandler handler) {
        return eventBus.addHandler(GraphEvent.TYPE, handler);
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        eventBus.fireEvent(event);
    }

    /**
     * 删除选择的目标对象
     */
    public void deleteSelectObjects() {
        //先删除  link  service network volume
        if (getSelectObjectList().isEmpty()) {
            return;
        }
        List<ServiceObject> serviceObjectList = new ArrayList<>();
        List<LinkObject> linkObjectList = new ArrayList<>();
        List<VolumeObject> volumeObjectList = new ArrayList<>();
        List<NetworkObject> networkObjectsList = new ArrayList<>();
        for (RendingObject object : getSelectObjectList()) {
            if (object instanceof ServiceObject) {
                serviceObjectList.add((ServiceObject) object);
            }
            if (object instanceof NetworkObject) {
                networkObjectsList.add((NetworkObject) object);
            }
            if (object instanceof VolumeObject) {
                volumeObjectList.add((VolumeObject) object);
            }
            if (object instanceof LinkObject) {
                linkObjectList.add((LinkObject) object);
            }
        }

        for (LinkObject linkObject : linkObjectList) {
            Link link = linkObject.getRefObject();
            RendingObject<?> linkSource = link.start;
            RendingObject<?> linkTarget = link.end;
            //移除link（Link 只需要从一端的节点移除即可）
            linkSource.removeLink(link.end, true);
        }
        linkObjectList.clear();
        for (ServiceObject serviceObject : serviceObjectList) {
            //移除所有的链接
            serviceObject.removeLink(null,true);
            serviceObjects.remove(serviceObject);
        }
        serviceObjectList.clear();

        for (NetworkObject networkObject : networkObjectsList) {
            //移除所有的链接
            networkObject.removeLink(null,true);
            networksObjects.remove(networkObject);
        }
        networkObjectsList.clear();

        for (VolumeObject volumeObject : volumeObjectList) {
            volumeObject.removeLink(null,true);
            volumeObjects.remove(volumeObject);
        }
        volumeObjectList.clear();
        clearSelected();
    }

    /**
     * 移除所有从 sourceObject出发的连接
     * @param sourceObject
     */
    public void removeSourceLinks(RendingObject<?> sourceObject) {
        linkObjects.removeIf(linkObject -> {
          return linkObject.getRefObject().start.equals(sourceObject);
        });
    }

    /**
     * 移除所有指向 targetObject的链接
     * @param targetObject
     */
    public void removeTargetLinks(RendingObject<?> targetObject) {
        linkObjects.removeIf(linkObject -> {
            if(linkObject.getRefObject().end.equals(targetObject))
            {
                //源头需要移除这个链接关联的数据,此操作不负责链接的删除
                linkObject.getRefObject().start.deleteLinkSource(linkObject);
                return true;
            }
            return false;
        });
    }
}
