CREATE KEYSPACE IF NOT EXISTS modernlrs WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};
CREATE TABLE IF NOT EXISTS  modernlrs.statements (
    id UUID,
    name varchar,
    address varchar,
    zip varchar,
    version int,
    primary key((id))
)