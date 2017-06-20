//package com.datadoghq.trace.instrument;
//
//import com.datadoghq.trace.DDTracer;
//import com.datadoghq.trace.writer.ListWriter;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.node.InternalSettingsPreparer;
//import org.elasticsearch.node.Node;
//import org.elasticsearch.node.NodeValidationException;
//import org.elasticsearch.plugins.Plugin;
//import org.elasticsearch.transport.Netty4Plugin;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.net.Inet4Address;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Date;
//
//import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
//
//
//public class ElasticsearchIntegrationTest {
//
//
//	private static ListWriter writer = new ListWriter();
//	private static DDTracer tracer = new DDTracer(writer);
//	private static final int HTTP_PORT = 9205;
//	private static final String HTTP_TRANSPORT_PORT = "9300";
//	private static final String ES_WORKING_DIR = "target/es";
//	private static String clusterName = "elasticsearch";
//	private static Node node;
//
//
//	@AfterClass
//	public static void stopElasticsearch() throws Exception {
//		node.close();
//	}
//
//	@BeforeClass
//	public static void warmup() throws NodeValidationException {
//
//
//		//GlobalTracer.register(tracer);
//
//
//		Settings settings = Settings.builder()
//				.put("path.home", ES_WORKING_DIR)
//				.put("path.data", ES_WORKING_DIR + "/data")
//				.put("path.logs", ES_WORKING_DIR + "/logs")
//				.put("transport.type", "netty4")
//				.put("http.type", "netty4")
//				.put("cluster.name", clusterName)
//				.put("http.port", HTTP_PORT)
//				.put("transport.tcp.port", HTTP_TRANSPORT_PORT)
//				.put("network.host", "0.0.0.0")
//				.build();
//		Collection plugins = Collections.singletonList(Netty4Plugin.class);
//		node = new PluginConfigurableNode(settings, plugins);
//		node.start();
//	}
//
//
//	@Test
//	public void testTransportClient() throws IOException {
//
//
//		Settings settings = Settings.builder()
//				.put("cluster.name", clusterName).build();
//
//		TransportClient client = new PreBuiltTransportClient(settings)
//				.addTransportAddress(new InetSocketTransportAddress(Inet4Address.getByName("localhost"), Integer.parseInt(HTTP_TRANSPORT_PORT)));
//
//		IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
//				.setSource(jsonBuilder()
//						.startObject()
//						.field("user", "kimchy")
//						.field("postDate", new Date())
//						.field("message", "trying out Elasticsearch")
//						.endObject()
//				)
//				.get();
//
//		//fixme works in debug, not in prod
////		assertThat(writer.getList().size()).isEqualTo(1);
//
//
//	}
//
//
//	private static class PluginConfigurableNode extends Node {
//
//		public PluginConfigurableNode(Settings settings,
//									  Collection<Class<? extends Plugin>> classpathPlugins) {
//			super(InternalSettingsPreparer.prepareEnvironment(settings, null), classpathPlugins);
//		}
//	}
//}
