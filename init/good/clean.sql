delete from distances where start_node is null or end_node is null;
CREATE INDEX d_start_idx ON distances (start_node);
CREATE INDEX d_end_idx ON distances (end_node);
--select * from cord_node c where not exists(select * from distances where c.id=start_node OR c.id=end_node)