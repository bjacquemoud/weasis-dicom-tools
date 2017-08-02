package org.weasis.dicom.param;

import java.io.IOException;

import org.dcm4che3.net.Association;
import org.weasis.dicom.util.StoreFromStreamSCU;

public class ForwardDestination {

    private final StoreFromStreamSCU streamSCU;
    private final DeviceOpService streamSCUService;
    private final AttributeEditor attributesEditor;
    private final boolean useDestinationAetForKeyMap;

    public ForwardDestination(DicomNode callingNode, DicomNode destinationNode) throws IOException {
        this(null, callingNode, destinationNode, null);
    }

    public ForwardDestination(AdvancedParams forwardParams, DicomNode callingNode, DicomNode destinationNode)
        throws IOException {
        this(forwardParams, callingNode, destinationNode, null);
    }

    public ForwardDestination(AdvancedParams forwardParams, DicomNode callingNode, DicomNode destinationNode,
        AttributeEditor attributesEditor) throws IOException {
        this(forwardParams, callingNode, destinationNode, false, attributesEditor);
    }

    public ForwardDestination(AdvancedParams forwardParams, DicomNode callingNode, DicomNode destinationNode,
        boolean useDestinationAetForKeyMap, AttributeEditor attributesEditor) throws IOException {
        this.streamSCU = new StoreFromStreamSCU(forwardParams, callingNode, destinationNode);
        this.streamSCUService = new DeviceOpService(streamSCU.getDevice());
        this.attributesEditor = attributesEditor;
        this.useDestinationAetForKeyMap = useDestinationAetForKeyMap;
    }

    public StoreFromStreamSCU getStreamSCU() {
        return streamSCU;
    }

    public DeviceOpService getStreamSCUService() {
        return streamSCUService;
    }

    public AttributeEditor getAttributesEditor() {
        return attributesEditor;
    }

    public boolean isUseDestinationAetForKeyMap() {
        return useDestinationAetForKeyMap;
    }

    public void stop() {
        Association as = streamSCU.getAssociation();
        if (as != null && as.isReadyForDataTransfer()) {
            as.abort();
        }
        streamSCUService.stop();
    }
}
