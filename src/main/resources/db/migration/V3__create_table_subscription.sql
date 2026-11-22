CREATE TABLE tbl_subscriptions (
   id UUID PRIMARY KEY NOT NULL ,
   user_id UUID NOT NULL,
   category_id UUID NOT NULL,
   name VARCHAR(150) NOT NULL,
   price DECIMAL(10,2) CHECK (price > 0) NOT NULL,
   billing_cycle VARCHAR(20) NOT NULL,
   status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
   payment_method VARCHAR(20) NOT NULL,
   first_billing_date DATE NOT NULL,
   alert_days INT CHECK (alert_days >= 0) NOT NULL DEFAULT 3,
   created_at TIMESTAMP NOT NULL,
   updated_at TIMESTAMP,

   CONSTRAINT fk_subscription_user
       FOREIGN KEY (user_id)
        REFERENCES tbl_users(id)
        ON DELETE CASCADE,

   CONSTRAINT fk_subscription_category
       FOREIGN KEY (category_id)
       REFERENCES tbl_categories(id)
       ON DELETE RESTRICT
);

    CREATE INDEX idx_subscriptions_user_id ON tbl_subscriptions(user_id);
    CREATE INDEX idx_subscriptions_category_id ON tbl_subscriptions(category_id);
