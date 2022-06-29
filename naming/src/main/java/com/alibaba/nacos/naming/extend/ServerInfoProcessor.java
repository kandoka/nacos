package com.alibaba.nacos.naming.extend;

import com.alibaba.nacos.core.cluster.ServerMemberManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ServerInfoProcessor {

    @Autowired
    private ServerMemberManager serverMemberManager;

    public boolean hasMember() {
        return this.serverMemberManager.allMembers().size() > 0;
    }
}