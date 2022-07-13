package com.venia.core.models.commerce;

import com.adobe.cq.commerce.core.components.models.common.Price;
import com.adobe.cq.commerce.core.components.models.product.Asset;
import com.adobe.cq.commerce.core.components.models.product.GroupItem;
import com.adobe.cq.commerce.core.components.models.product.Product;
import com.adobe.cq.commerce.core.components.models.product.Variant;
import com.adobe.cq.commerce.core.components.models.product.VariantAttribute;
import com.adobe.cq.commerce.core.components.models.retriever.AbstractProductRetriever;
import com.adobe.cq.commerce.core.components.storefrontcontext.ProductStorefrontContext;
import com.shopify.graphql.support.SchemaViolationError;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class, adapters = MyProduct.class, resourceType = MyProductImpl.RESOURCE_TYPE)
public class MyProductImpl implements MyProduct {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyProductImpl.class);

    protected static final String RESOURCE_TYPE = "venia/components/commerce/product";

    private static final String IMAGE_ATTRIBUTE = "image_url";
    private static final String IMAGE_1_ATTRIBUTE = "image_url1";
    private static final String IMAGE_2_ATTRIBUTE = "image_url2";

    private AbstractProductRetriever productRetriever;

    @Self
    @Via(type = ResourceSuperType.class)
    private Product product;

    @PostConstruct
    public void initModel() {
        productRetriever = product.getProductRetriever();

        if (productRetriever != null) {
            productRetriever.extendProductQueryWith(p -> p.createdAt()
                    .addCustomSimpleField(IMAGE_ATTRIBUTE)
                    .addCustomSimpleField(IMAGE_1_ATTRIBUTE)
                    .addCustomSimpleField(IMAGE_2_ATTRIBUTE));
        }
    }

    @Override
    public Boolean getFound() {
        return product.getFound();
    }

    @Override
    public String getName() {
        return product.getName();
    }

    @Override
    public String getDescription() {
        return product.getDescription();
    }

    @Override
    public String getSku() {
        return product.getSku();
    }

    @Override
    public Price getPriceRange() {
        return product.getPriceRange();
    }

    @Override
    public Boolean getInStock() {
        return product.getInStock();
    }

    @Override
    public Boolean isConfigurable() {
        return product.isConfigurable();
    }

    @Override
    public Boolean isGroupedProduct() {
        return product.isGroupedProduct();
    }

    @Override
    public Boolean isVirtualProduct() {
        return product.isVirtualProduct();
    }

    @Override
    public Boolean isBundleProduct() {
        return product.isBundleProduct();
    }

    @Override
    public String getVariantsJson() {
        return product.getVariantsJson();
    }

    @Override
    public List<Variant> getVariants() {
        return product.getVariants();
    }

    @Override
    public List<GroupItem> getGroupedProductItems() {
        return product.getGroupedProductItems();
    }

    @Override
    public List<Asset> getAssets() {
        return product.getAssets();
    }

    @Override
    public List<AssetImpl> getProductAssets() {

        String imageUrl = "";
        String image1Url = "";
        String image2Url = "";
        try {
            imageUrl = productRetriever.fetchProduct().getAsString(IMAGE_ATTRIBUTE);
            image1Url = productRetriever.fetchProduct().getAsString(IMAGE_1_ATTRIBUTE);
            image2Url = productRetriever.fetchProduct().getAsString(IMAGE_2_ATTRIBUTE);
        } catch (SchemaViolationError e) {
            LOGGER.error("Error retrieving image urls");
        }
        LOGGER.info("Image URL ## {}", imageUrl);
        LOGGER.info("Image 1 URL ## {}", image1Url);
        LOGGER.info("Image 2 URL ## {}", image2Url);

        List<AssetImpl> assetList = new ArrayList<>();

        AssetImpl asset1 = new AssetImpl();
        asset1.setLabel("Asset URL 1");
        asset1.setPosition(1);
        asset1.setType("image");
        //asset1.setPath("http://3.229.31.9/media/catalog/product/cache/3ad5bf274214162e1664ccaf6be141d3/i/s/istockphoto-1307086567-170667a.jpg");
        asset1.setPath(imageUrl);
        assetList.add(asset1);

        AssetImpl asset2 = new AssetImpl();
        asset2.setLabel("Asset URL 2");
        asset2.setPosition(2);
        asset2.setType("image");
        //asset2.setPath("http://3.229.31.9/media/catalog/product/cache/3ad5bf274214162e1664ccaf6be141d3/c/a/car_var1.jpg");
        asset2.setPath(image1Url);
        assetList.add(asset2);

        AssetImpl asset3 = new AssetImpl();
        asset3.setLabel("Asset URL 3");
        asset3.setPosition(3);
        asset3.setType("image");
        //asset3.setPath("https://media.ed.edmunds-media.com/bmw/3-series/2021/oem/2021_bmw_3-series_sedan_330e_fq_oem_4_815.jpg");
        asset3.setPath(image2Url);
        assetList.add(asset3);

        return assetList;
    }

    @Override
    public String getAssetsJson() {

        return "[{\"label\":\"Asset URL 1\",\"position\":1,\"type\":\"image\",\"path\":\"https://media.ed.edmunds-media.com/bmw/3-series/2021/oem/2021_bmw_3-series_sedan_330e_fq_oem_1_815.jpg\"},{\"label\":\"Asset URL 2\",\"position\":2,\"type\":\"image\",\"path\":\"https://media.ed.edmunds-media.com/bmw/3-series/2021/oem/2021_bmw_3-series_sedan_330e_fq_oem_2_815.jpg\"},{\"label\":\"Asset URL 3\",\"position\":3,\"type\":\"image\",\"path\":\"https://media.ed.edmunds-media.com/bmw/3-series/2021/oem/2021_bmw_3-series_sedan_330e_fq_oem_3_815.jpg\"}]";
    }

    @Override
    public List<VariantAttribute> getVariantAttributes() {
        return product.getVariantAttributes();
    }

    @Override
    public Boolean loadClientPrice() {
        return product.loadClientPrice();
    }

    @Override
    public AbstractProductRetriever getProductRetriever() {
        return product.getProductRetriever();
    }

    @Override
    public ProductStorefrontContext getStorefrontContext() {
        return product.getStorefrontContext();
    }

    @Override
    public String getMetaDescription() {
        return product.getMetaDescription();
    }

    @Override
    public String getMetaKeywords() {
        return product.getMetaKeywords();
    }

    @Override
    public String getMetaTitle() {
        return product.getMetaTitle();
    }

    @Override
    public String getCanonicalUrl() {
        return product.getCanonicalUrl();
    }
}
