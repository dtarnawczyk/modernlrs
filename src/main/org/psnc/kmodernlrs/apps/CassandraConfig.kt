package org.psnc.kmodernlrs.repo

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.cassandra.mapping.CassandraMappingContext
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext
import org.springframework.data.cassandra.convert.CassandraConverter
import org.springframework.data.cassandra.convert.MappingCassandraConverter
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean
import io.netty.util.internal.PlatformDependent.getObject
import org.springframework.data.cassandra.config.SchemaAction
import org.springframework.data.cassandra.core.CassandraOperations
import org.springframework.data.cassandra.core.CassandraTemplate
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean

@ConditionalOnProperty(name=arrayOf("database.type"), havingValue="cassandra")
@Configuration
open class CassandraConfig {

    @Value("&{database.cassandra.keyspace}")
    lateinit var keyspace:String

    @Value("&{database.cassandra.contactpoints}")
    lateinit var contactpoints:String

    @Value("&{database.cassandra.port}")
    lateinit var port:String

    @Bean
    open fun cluster(): CassandraClusterFactoryBean {
        val cluster = CassandraClusterFactoryBean()
        cluster.setContactPoints(contactpoints)
        cluster.setPort(port.toInt())
        return cluster
    }

    @Bean
    open fun mappingContext(): CassandraMappingContext {
        return BasicCassandraMappingContext()
    }

    @Bean
    open fun converter(): CassandraConverter {
        return MappingCassandraConverter(mappingContext())
    }

    @Bean
    open fun session(): CassandraSessionFactoryBean {
        val cassandraSessionFactoryBean = CassandraSessionFactoryBean()
        cassandraSessionFactoryBean.setCluster(cluster().getObject())
        cassandraSessionFactoryBean.setKeyspaceName(keyspace)
        cassandraSessionFactoryBean.setConverter(converter())
        cassandraSessionFactoryBean.setSchemaAction(SchemaAction.NONE)
        return cassandraSessionFactoryBean
    }

    @Bean
    open fun cassandraTemplate(): CassandraOperations {
        return CassandraTemplate(session().getObject())
    }

}