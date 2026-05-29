-- Create all required databases
CREATE DATABASE auth_service_db;
CREATE DATABASE inventory_db;
CREATE DATABASE sales_db;
CREATE DATABASE analytics_db;
CREATE DATABASE notification_db;

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE auth_service_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE inventory_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE sales_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE analytics_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE notification_db TO postgres;
