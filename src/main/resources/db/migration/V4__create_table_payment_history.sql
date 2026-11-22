CREATE TABLE tbl_payment_history (
    id UUID PRIMARY KEY NOT NULL,
    subscription_id UUID NOT NULL,
    amount DECIMAL(10,2) NOT NULL CHECK (amount > 0),
    payment_date DATE NOT NULL,
    reference_period VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_payment_history_subscription
        FOREIGN KEY (subscription_id)
        REFERENCES tbl_subscriptions(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_payment_subscription_period
        UNIQUE (subscription_id, reference_period)
);

CREATE INDEX idx_payment_history_subscription_id
    ON tbl_payment_history(subscription_id);