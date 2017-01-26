package org.lrs.kmodernlrs

object ApiEndpoints {
	const val API_PATH = "/v1/"
    // Xapi services
	const val STATEMENTS_ENDPOINT = "/xAPI/statements"
    const val AGENTS_ENDPOINT = "/xAPI/agents"
    const val AGENTS_PROFILE_ENDPOINT = "/xAPI/agents/profile"
    const val ACTIVITIES_ENDPOINT = "/xAPI/activities"
    const val ACTIVITIES_PROFILE_ENDPOINT = "/xAPI/activities/profile"
    const val ACTIVITIES_STATE_ENDPOINT = "/xAPI/activities/state"
    const val ABOUT_ENDPOINT = "/xAPI/about"

    // Spring Boot Actuator services
    const val AUTOCONFIG_ENDPOINT = "/autoconfig"
    const val BEANS_ENDPOINT = "/beans"
    const val CONFIGPROPS_ENDPOINT = "/configprops"
    const val ENV_ENDPOINT = "/env"
    const val MAPPINGS_ENDPOINT = "/mappings"
    const val METRICS_ENDPOINT = "/metrics"
    const val SHUTDOWN_ENDPOINT = "/shutdown"
}