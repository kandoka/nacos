package com.alibaba.nacos.naming.extend;

import com.alibaba.nacos.core.cluster.Member;
import com.alibaba.nacos.core.cluster.NodeState;
import com.alibaba.nacos.core.cluster.ServerMemberManager;
import com.alibaba.nacos.sys.env.EnvUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.ConfigurableEnvironment;

import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.ServletContext;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FTPTest {

    @Mock
    private ServletContext servletContext;

    @Mock
    private ConfigurableEnvironment environment;

    @InjectMocks
    ServerInfoProcessor serverInfoProcessor;

    ServerMemberManager serverMemberManager;

    @Before
    public void init() throws Exception {
        when(environment.getProperty("server.port", Integer.class, 8848)).thenReturn(8848);
        when(environment.getProperty("nacos.member-change-event.queue.size", Integer.class, 128)).thenReturn(128);
        EnvUtil.setEnvironment(environment);
        EnvUtil.setIsStandalone(true);
        when(servletContext.getContextPath()).thenReturn("");
        serverMemberManager = new ServerMemberManager(servletContext);

        Member self = Member.builder().ip("1.1.1.1").port(8848).state(NodeState.UP).build();
        List<Member> members = new LinkedList<>();
        members.add(self);

        ReflectionTestUtils.setField(serverMemberManager, "self", self);
        ReflectionTestUtils.setField(serverInfoProcessor, "serverMemberManager", serverMemberManager);
    }

    @Test
    public void test() {
        boolean hasMember = serverInfoProcessor.hasMember();
        servletContext.getContextPath();
        Assert.assertTrue("no members here", hasMember);
    }

}