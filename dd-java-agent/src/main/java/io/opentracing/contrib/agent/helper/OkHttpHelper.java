package io.opentracing.contrib.agent.helper;

import io.opentracing.contrib.okhttp3.TracingInterceptor;
import okhttp3.OkHttpClient;
import org.jboss.byteman.rule.Rule;

import java.util.Collections;

import static io.opentracing.contrib.okhttp3.OkHttpClientSpanDecorator.STANDARD_TAGS;


/**
 * Patch the OkHttp Client during the building steps.
 */
public class OkHttpHelper extends DDAgentTracingHelper<OkHttpClient.Builder> {


	public OkHttpHelper(Rule rule) {
		super(rule);
	}

	/**
	 * Strategy: Just before the okhttp3.OkHttpClient$Builder.build() method called, we add a new interceptor for the tracing
	 * part.
	 *
	 * @param builder The builder instance to patch
	 * @return The same builder instance with a new tracing interceptor
	 * @throws Exception
	 */
	protected OkHttpClient.Builder doPatch(OkHttpClient.Builder builder) throws Exception {
		TracingInterceptor interceptor = new TracingInterceptor(tracer, Collections.singletonList(STANDARD_TAGS));
		builder.addInterceptor(interceptor);
		builder.addNetworkInterceptor(interceptor);

		return builder;
	}
}