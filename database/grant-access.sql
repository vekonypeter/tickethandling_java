GRANT SELECT, INSERT, UPDATE, DELETE ON component, customer, comp_in_system, employee, role, emprole, priority, status, ticket TO tickethandling_role;

GRANT USAGE, SELECT, UPDATE ON comp_in_system_cis_id_seq TO tickethandling_role;