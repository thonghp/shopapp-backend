package com.example.shopapp.models;

public enum OrderStatus {
    PENDING {
        @Override
        public String defaultDescription() {
            return "pending";
        }
    },

    PROCESSING {
        @Override
        public String defaultDescription() {
            return "processing";
        }
    },

    SHIPPING {
        @Override
        public String defaultDescription() {
            return "Shipping";
        }
    },

    DELIVERED {
        @Override
        public String defaultDescription() {
            return "delivered";
        }
    },

    CANCELLED {
        @Override
        public String defaultDescription() {
            return "cancelled";
        }
    };

    public abstract String defaultDescription();
}
