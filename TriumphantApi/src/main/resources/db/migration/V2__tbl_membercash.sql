-- Migration script to create the membercash table
CREATE TABLE membercash (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    member_id UUID,  -- The foreign key to the Member table
    month VARCHAR(9) NOT NULL,  -- Store the month as a string or enum
    cash FLOAT NOT NULL,
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES tbl_tcimember(id) ON DELETE CASCADE
);

-- Optionally, add an index on member_id if frequently queried
CREATE INDEX idx_member_id ON membercash (member_id);
