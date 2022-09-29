import { gql } from '@apollo/client';

export const GET_CHECKCOD_CONFIG_DATA = gql`
    query storeConfigData {
        storeConfig {
            id
            payment_cashondelivery_payable_to @client
            payment_cashondelivery_mailing_address @client
        }
    }
`;

export const SET_PAYMENT_METHOD_ON_CART = gql`
    mutation setPaymentMethodOnCart($cartId: String!) {
        setPaymentMethodOnCart(
            input: { cart_id: $cartId, payment_method: { code: "cashondelivery" } }
        ) @connection(key: "setPaymentMethodOnCart") {
            cart {
                id
                selected_payment_method {
                    code
                    title
                }
            }
        }
    }
`;

export default {
    getCheckcodConfigQuery: GET_CHECKCOD_CONFIG_DATA,
    setPaymentMethodOnCartMutation: SET_PAYMENT_METHOD_ON_CART
};
