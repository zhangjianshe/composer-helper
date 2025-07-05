package cn.cangling.docker.composer.client.composer.model;


import elemental2.dom.*;
import jsinterop.base.Js;

import java.util.ArrayList;
import java.util.List;

public class ServiceObject extends RendingObject<Service> {


    List<ImageObject<String>> networks;
    List<ImageObject<String>> volumes;
    List<PortObject> ports;
    RoundBoxObject networkBox;
    RoundBoxObject volumeBox;
    RoundBoxObject dependencyBox;//依赖框
    ImageObject<String> iconObject;
    LabelObject<String> titleObject;

    public ServiceObject(Service service) {
        setRefObject(service);
        networks = new ArrayList<>();
        volumes = new ArrayList<>();
        ports = new ArrayList<>();
        layout();
    }


    public boolean linkToNetwork(Network network) {
        boolean b = getRefObject().addNetwork(network.name);
        if (b) {
            layout();
        }
        return b;
    }

    public boolean linkToVolume(Volume volume) {
        boolean b = getRefObject().addVolume(volume.name);
        if (b) {
            layout();
        }
        return b;
    }

    /**
     * 链接到另外一个服务
     *
     * @param service
     * @return
     */
    public boolean linkToDependency(Service service) {
        boolean b = getRefObject().addDependency(service.name);
        if (b) {
            layout();
        }
        return b;
    }

    /**
     * 根据数据布局相关的子组件信息
     */
    public void layout() {
        Rect rect = new Rect(0, 0, 0, 0);
        double boxWidth = 110;
        double boxHeight = 90;
        int ICON_WIDTH = 24;
        double MARGIN = 5;
        double PADDING = 5;

        boxHeight = Math.max(boxHeight, getRefObject().ports.length * 10. + 4 * PADDING);

        rect.setHeight(boxHeight);
        getRect().setHeight(boxHeight);

        volumes.clear();

        double startX = rect.x + PADDING;
        double startY = rect.y + rect.height + PADDING + MARGIN;

        //添加一个硬盘的拖动图标
        ImageObject<String> imageObject = new ImageObject<>(Images.getHard(), null);
        imageObject.rect(startX, startY, ICON_WIDTH, ICON_WIDTH);
        volumes.add(imageObject);
        imageObject.setParent(this);
        startX += ICON_WIDTH;

        for (int i = 0; i < getRefObject().volumes.length; i++) {
            String volumeName = getRefObject().volumes.getAt(i);
            imageObject = new ImageObject<>(Images.getHardRed(), volumeName);
            imageObject.rect(startX, startY, ICON_WIDTH, ICON_WIDTH);
            volumes.add(imageObject);
            imageObject.setParent(this);
            startX += ICON_WIDTH;
        }

        if (volumeBox == null) {
            volumeBox = new RoundBoxObject(5);
        }
        volumeBox.setWidth(1);
        volumeBox.setDash(null);
        volumeBox.setColor("#d0d0d0");
        volumeBox.rect(rect.x, rect.y + rect.height + MARGIN,
                PADDING + PADDING + ICON_WIDTH * (volumes.size()),
                PADDING + PADDING + ICON_WIDTH);
        volumeBox.setParent(this);

        startX += PADDING;
        startX += PADDING;
        startX += PADDING;
        networks.clear();

        imageObject = new ImageObject<>(Images.getNet(), null);
        imageObject.rect(startX, startY, ICON_WIDTH, ICON_WIDTH);
        networks.add(imageObject);
        imageObject.setParent(this);

        for (int i = 0; i < getRefObject().networks.length; i++) {
            String networkName = getRefObject().networks.getAt(i);
            startX += ICON_WIDTH;
            imageObject = new ImageObject<>(Images.getNetRed(), networkName);
            imageObject.rect(startX, startY, ICON_WIDTH, ICON_WIDTH);
            networks.add(imageObject);
            imageObject.setParent(this);
        }

        if (networkBox == null) {
            networkBox = new RoundBoxObject(5);
        }

        networkBox.rect(volumeBox.getRect().x + volumeBox.getRect().width + PADDING,
                rect.y + rect.height + MARGIN,
                PADDING + PADDING + ICON_WIDTH * (networks.size()), PADDING + PADDING + ICON_WIDTH);
        networkBox.setWidth(1);
        networkBox.setDash(null);
        networkBox.setColor("#d0d0d0");
        networkBox.setParent(this);

        boxWidth = Math.max(boxWidth,
                networkBox.getRect().x + networkBox.getRect().width
        );

        //依赖链接框
        if (dependencyBox == null) {
            dependencyBox = new RoundBoxObject(5);
            dependencyBox.rect(-10, 20, 15, 40);
            dependencyBox.setFill("darkgray");
            dependencyBox.setParent(this);
        }


        //计算标题的宽度
        OffscreenCanvas canvas = new OffscreenCanvas(10, 10);
        CanvasRenderingContext2D context = Js.uncheckedCast(canvas.getContext("2d"));
        context.font = "bold 16px 'Microsoft YaHei'";
        TextMetrics textMetrics = context.measureText(getRefObject().name);
        double labelWidth = textMetrics.width;
        double labelHeight = textMetrics.actualBoundingBoxAscent + textMetrics.actualBoundingBoxDescent;

        boxWidth = Math.max(boxWidth, labelWidth);
        getRect().setWidth(boxWidth);
        rect.setWidth(boxWidth);

        if (titleObject == null) {
            titleObject = new LabelObject<>(getRefObject().name);
            titleObject.rect((boxWidth - labelWidth) / 2, rect.y + 10, labelWidth, labelHeight);
            titleObject.setParent(this);
        }

        //布局图标的位置
        int BIG_ICON_WIDTH = 48;
        if (iconObject == null && getRefObject().image != null && !getRefObject().image.isEmpty()) {
            Image image = new Image();
            image.src = getRefObject().image;
            iconObject = new ImageObject<>(image, "");
            iconObject.rect((boxWidth - BIG_ICON_WIDTH) / 2., boxHeight - BIG_ICON_WIDTH - 10, BIG_ICON_WIDTH, BIG_ICON_WIDTH);
            iconObject.setParent(this);
        }

        //布局port的位置
        double portY = 2 * PADDING;
        double ITEM_HEIGHT = 24;
        for (String port : getRefObject().ports.asList()) {
            PortObject portObject = new PortObject(port);
            portObject.rect(boxWidth, portY, 30, ITEM_HEIGHT);
            portObject.setParent(this);
            portY += ITEM_HEIGHT;
            ports.add(portObject);
        }

    }

    @Override
    public void draw(CanvasRenderingContext2D context2D, double t) {

        Rect drawingRect1 = getDrawingRect();

        //绘制依赖框
        dependencyBox.draw(context2D, t);
        context2D.fillStyle = BaseRenderingContext2D.FillStyleUnionType.of("skyblue");
        context2D.beginPath();
        roundRect(context2D, drawingRect1.x, drawingRect1.y, drawingRect1.width, drawingRect1.height, 10);
        context2D.fill();
        for (ImageObject<String> network : networks) {
            network.draw(context2D, t);
        }

        if (networkBox != null) {
            networkBox.draw(context2D, t);
        }
        for (ImageObject<String> volume : volumes) {
            volume.draw(context2D, t);
        }

        for (PortObject portObject : ports) {
            portObject.draw(context2D, t);
        }

        if (volumeBox != null) {
            volumeBox.draw(context2D, t);
        }
        if (iconObject != null) {
            iconObject.draw(context2D, t);
        }
        if (titleObject != null) {
            titleObject.draw(context2D, t);
        }
        drawSelectedBorder(context2D, t, 10);
    }

    public Rect getBorderRect() {
        Rect rect = new Rect();
        rect.copyFrom(getDrawingRect());
        rect.shrink(-10, 15, 50, -15);
        return rect;
    }

    @Override
    public Point getLinkCenter(RendingObject relativeObject, boolean relativeObjectIsEnd) {
        if (relativeObject instanceof NetworkObject) {
            NetworkObject networkObject = (NetworkObject) relativeObject;
            Network targetNetwork = networkObject.getRefObject();
            String networkName = targetNetwork.name;
            for (ImageObject imageObject : networks) {
                if (imageObject.getData() instanceof String) {
                    String network = (String) imageObject.getData();
                    if (network.equals(networkName)) {
                        Point pt = new Point();
                        Point center = imageObject.getDrawingRect().center();
                        pt.set(center.x, imageObject.getDrawingRect().y + imageObject.getDrawingRect().height + 5);
                        return pt;
                    }
                }
            }
            return getDrawingRect().center();
        } else if (relativeObject instanceof VolumeObject) {
            VolumeObject volumeObject = (VolumeObject) relativeObject;
            Volume targetVolume = volumeObject.getRefObject();
            String volumeName = targetVolume.name;
            for (ImageObject imageObject : volumes) {
                if (imageObject.getData() instanceof String) {
                    String volume = (String) imageObject.getData();
                    if (volume.equals(volumeName)) {
                        Point pt = new Point();
                        Point center = imageObject.getDrawingRect().center();
                        pt.set(center.x, imageObject.getDrawingRect().y + imageObject.getDrawingRect().height + 5);
                        return pt;
                    }
                }
            }
            return getDrawingRect().center();
        } else if (relativeObject instanceof ServiceObject) {
            if (relativeObjectIsEnd) {
                return dependencyBox.getDrawingRect().center();
            } else {
                Point pointOnEdge = getDrawingRect().checkIntersectionPointToCenter(relativeObject.getDrawingRect().center());
                if (pointOnEdge == null) {
                    return dependencyBox.getDrawingRect().center();
                } else {
                    return pointOnEdge;
                }
            }
        }
        return super.getLinkCenter(relativeObject, relativeObjectIsEnd);
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected) {
            //显示与这个服务连接的网络和卷
            List<LinkObject> links = getGraph().findSourceLinkObject(this);
            for (LinkObject linkObject : links) {
                linkObject.setVisible(true);
            }
        } else {
            List<LinkObject> links = getGraph().findSourceLinkObject(this);
            for (LinkObject linkObject : links) {
                linkObject.setVisible(false);
            }
        }
        if (titleObject != null) {
            titleObject.setSelected(selected);
        }
        super.setSelected(selected);
    }

    @Override
    public HitTestResult hitTest(double canvasX, double canvasY) {
        HitTestResult hitTest = super.hitTest(canvasX, canvasY);
        if (hitTest.getHitTest() == HitTest.HT_NONE) {
            if (volumes.get(0).hitTest(canvasX, canvasY).getHitTest() == HitTest.HT_OBJECT) {
                return HitTestResult.create(HitTest.HT_START_VOLUME, volumes.get(0));
            }
            if (networks.get(0).hitTest(canvasX, canvasY).getHitTest() == HitTest.HT_OBJECT) {
                return HitTestResult.create(HitTest.HT_START_NETWORK, networks.get(0));
            }
            if (dependencyBox.hitTest(canvasX, canvasY).getHitTest() == HitTest.HT_OBJECT) {
                return HitTestResult.create(HitTest.HT_START_DEPENDENCY, dependencyBox);
            }
            return HitTestResult.create(HitTest.HT_NONE, null);
        }
        switch (hitTest.getHitTest()) {
            case HT_OBJECT:
                return hitTest;
            case HT_NONE:
            default:
                return hitTest;
        }
    }

    @Override
    public void removeLink(RendingObject<?> relativeObject, boolean relativeObjectIsEnd) {
        if (relativeObject == null) {
            //remove all the links
            getGraph().removeSourceLinks(this);
            getGraph().removeTargetLinks(this);
            getRefObject().dependsOn.setLength(0);
            getRefObject().volumes.setLength(0);
            getRefObject().networks.setLength(0);
            return;
        }
        if (relativeObjectIsEnd) {
            Service thisService = getRefObject();
            if (relativeObject instanceof ServiceObject) {
                Service targetService = ((ServiceObject) relativeObject).getRefObject();
                int index = thisService.dependsOn.indexOf(targetService.name);
                if (index >= 0) {
                    thisService.dependsOn.splice(index, 1);
                }

            } else if (relativeObject instanceof NetworkObject) {
                Network targetNetwork = ((NetworkObject) relativeObject).getRefObject();
                int index = thisService.networks.indexOf(targetNetwork.name);
                if (index >= 0) {
                    thisService.networks.splice(index, 1);
                }
            } else if (relativeObject instanceof VolumeObject) {
                Volume targetVolume = ((VolumeObject) relativeObject).getRefObject();
                int index = thisService.volumes.indexOf(targetVolume.name);
                if (index >= 0) {
                    thisService.volumes.splice(index, 1);
                }
            }
        }
    }

    /**
     * 删除 link start 对应的数据 不删除链接本身
     *
     * @param linkObject
     */
    public void deleteLinkSource(LinkObject linkObject) {
        Link link = linkObject.getRefObject();
        RendingObject<?> linkTarget = link.end;
        if (linkTarget instanceof ServiceObject) {
            ServiceObject serviceObject = (ServiceObject) linkTarget;
            switch (link.kind) {
                case LINK_KIND_DEPENDENCY: {
                    deleteDependency(serviceObject.getRefObject().name);
                    break;
                }
            }
        } else if (linkTarget instanceof NetworkObject) {
            NetworkObject networkObject = (NetworkObject) linkTarget;
            switch (link.kind) {
                case LINK_KIND_UNKNOWN:
                    deleteNetwork(networkObject.getRefObject().name);
                    break;
            }
        } else if (linkTarget instanceof VolumeObject) {
            VolumeObject volumeObject = (VolumeObject) linkTarget;
            switch (link.kind) {
                case LINK_KIND_UNKNOWN:
                    deleteVolume(volumeObject.getRefObject().name);
                    break;
            }
        }

    }

    private void deleteVolume(String name) {
        if (name != null && !name.isEmpty()) {
            int i = getRefObject().volumes.indexOf(name);
            if (i >= 0) {
                getRefObject().volumes.splice(i, 1);
            }
        }
    }

    private void deleteNetwork(String name) {
        if (name != null && !name.isEmpty()) {
            int i = getRefObject().networks.indexOf(name);
            if (i >= 0) {
                getRefObject().networks.splice(i, 1);
            }
        }
    }

    private void deleteDependency(String name) {
        if (name != null && !name.isEmpty()) {
            int i = getRefObject().dependsOn.indexOf(name);
            if (i >= 0) {
                getRefObject().dependsOn.splice(i, 1);
            }
        }
    }
}
