create USER hibernate_demo PASSWORD 'demo123';


GRANT CONNECT ON DATABASE postgres TO hibernate_demo;
GRANT USAGE ON SCHEMA public TO hibernate_demo;