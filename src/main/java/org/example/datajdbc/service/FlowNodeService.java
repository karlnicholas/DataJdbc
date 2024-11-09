package org.example.datajdbc.service;

import org.apache.commons.lang3.StringUtils;
import org.example.datajdbc.domain.FlowNode;
import org.example.datajdbc.repository.FlowNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FlowNodeService {

    private final FlowNodeRepository flowNodeRepository;
    private final Map<String, FlowNode> flowNodeCache = new ConcurrentHashMap<>();

    @Autowired
    public FlowNodeService(FlowNodeRepository flowNodeRepository) {
        this.flowNodeRepository = flowNodeRepository;
    }

    public void initialize() {
        flowNodeRepository.findAll().forEach(flowNode -> {
            String paddedSlic = padRight(flowNode.getSlic());
            flowNode.setSlic(paddedSlic);  // Ensure padded slic is stored in cache
            flowNodeCache.put(createKey(flowNode.getCc(), paddedSlic, flowNode.getSort()), flowNode);
        });
    }

    public FlowNode getOrCreateFlowNode(String cc, String slic, String sort) {
        String paddedSlic = padRight(slic);  // Always pad slic
        String key = createKey(cc, paddedSlic, sort);

        return flowNodeCache.computeIfAbsent(key, k -> {
            FlowNode newFlowNode = FlowNode.builder()
                    .cc(cc)
                    .slic(paddedSlic)
                    .sort(sort)
                    .build();
            FlowNode savedFlowNode = flowNodeRepository.save(newFlowNode);
            flowNodeCache.put(k, savedFlowNode);
            return savedFlowNode;
        });
    }

    private String createKey(String cc, String slic, String sort) {
        return cc + ":" + slic + ":" + sort;
    }

    private String padRight(String slic) {
        return StringUtils.rightPad(slic, 5);  // Right-pads slic to 5 characters
    }
}
