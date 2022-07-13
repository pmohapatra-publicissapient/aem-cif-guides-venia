/** Place this (replace the existing file) in the folder aem-cif-guides-venia\ui.frontend\node_modules\@magento\peregrine\lib\talons\CartPage\PriceSummary */
import { gql } from '@apollo/client';

import { DiscountSummaryFragment } from './discountSummary.gql';
//import { GiftCardSummaryFragment } from './queries/giftCardSummary';
import { ShippingSummaryFragment } from './shippingSummary.gql';
import { TaxSummaryFragment } from './taxSummary.gql';

export const GrandTotalFragment = gql`
    fragment GrandTotalFragment on CartPrices {
        grand_total {
            currency
            value
        }
    }
`;

export const PriceSummaryFragment = gql`
    fragment PriceSummaryFragment on Cart {
        id
        items {
            id
            quantity
        }
        ...ShippingSummaryFragment
        prices {
            ...TaxSummaryFragment
            ...DiscountSummaryFragment
            ...GrandTotalFragment
            subtotal_excluding_tax {
                currency
                value
            }
        }
    }
    ${DiscountSummaryFragment}
    ${GrandTotalFragment}
    ${ShippingSummaryFragment}
    ${TaxSummaryFragment}
`;
