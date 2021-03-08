package connect.appinsights;

import com.microsoft.applicationinsights.TelemetryConfiguration;
import com.microsoft.applicationinsights.logback.ApplicationInsightsAppender;
import connect.logger.ILoggerWriter;
import connect.logger.Logger;
import connect.models.AssetRequest;
import connect.models.IdModel;
import connect.models.Listing;
import connect.models.TierConfigRequest;
import haxe.root.Array;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AzureLoggerWriter implements ILoggerWriter {
    private final ch.qos.logback.classic.Logger logger;
    private final ApplicationInsightsAppender appender;
    private final Map<String, String> properties;

    public AzureLoggerWriter(String instrumentationKey) {
        TelemetryConfiguration.getActive().setInstrumentationKey(instrumentationKey);
        final String name = ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME;
        logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(name);
        appender = (ApplicationInsightsAppender) logger.getAppender("aiAppender");
        properties = new HashMap<>();
    }

    protected AzureLoggerWriter(ch.qos.logback.classic.Logger logger, ApplicationInsightsAppender appender) {
        this.logger = logger;
        this.appender = appender;
        this.properties = new HashMap<>();
    }

    @Override
    public boolean setFilename(String filename) {
        return false;
    }

    @Override
    public String getFilename() {
        return "";
    }

    @Override
    public void writeLine(int level, String line) {
        final Map<String, String> azureProperties = appender.getTelemetryClientProxy()
                .getTelemetryClient().getContext().getProperties();
        azureProperties.clear();
        azureProperties.putAll(properties);
        if (level == Logger.LEVEL_ERROR) {
            logger.error(line);
        } else if (level == Logger.LEVEL_WARNING) {
            logger.warn(line);
        } else if (level == Logger.LEVEL_INFO) {
            logger.info(line);
        } else if (level == Logger.LEVEL_DEBUG) {
            logger.debug(line);
        } else {
            logger.trace(line);
        }
        azureProperties.clear();
    }

    @Override
    public ILoggerWriter copy(IdModel request) {
        final AzureLoggerWriter writer = new AzureLoggerWriter(logger, appender);
        if (request != null) {
            final String providerId = getProviderId(request);
            final String hubId = getHubId(request);
            final String marketplaceId = getMarketplaceId(request);
            final String productId = getProductId(request);
            final String tierAccountId = getTierAccountId(request);
            final String connectionId = getConnectionId(request);
            final String vendorId = getVendorId(request);
            final String externalId = getExternalId(request);
            if (request.id != null) {
                properties.put("Request", request.id);
            }
            if (providerId != null) {
                properties.put("Provider", providerId);
            }
            if (hubId != null) {
                properties.put("Hub", hubId);
            }
            if (marketplaceId != null) {
                properties.put("Marketplace", marketplaceId);
            }
            if (productId != null) {
                properties.put("Product", productId);
            }
            if (tierAccountId != null) {
                properties.put("TierAccount", tierAccountId);
            }
            if (connectionId != null) {
                properties.put("Connection", connectionId);
            }
            if (vendorId != null) {
                properties.put("Vendor", vendorId);
            }
            if (externalId != null) {
                properties.put("ExternalId", externalId);
            }
        }
        return writer;
    }

    private static String getProviderId(IdModel request) {
        try {
            try {
                return ((AssetRequest)request).asset.connection.provider.id;
            } catch (ClassCastException ignored) {
            }
            try {
                return ((TierConfigRequest)request).configuration.connection.provider.id;
            } catch (ClassCastException ignored) {
            }
            try {
                return ((Listing)request).provider.id;
            } catch (ClassCastException ignored) {
            }
        } catch (NullPointerException ignored) {
        }
        return null;
    }

    private static String getHubId(IdModel request) {
        try {
            try {
                return ((AssetRequest)request).asset.connection.hub.id;
            } catch (ClassCastException ignored) {
            }
            try {
                return ((TierConfigRequest)request).configuration.connection.hub.id;
            } catch (ClassCastException ignored) {
            }
        } catch (NullPointerException ignored) {
        }
        return null;
    }

    private static String getMarketplaceId(IdModel request) {
        try {
            try {
                return ((AssetRequest)request).marketplace.id;
            } catch (ClassCastException ignored) {
            }
            try {
                return ((TierConfigRequest)request).marketplace.id;
            } catch (ClassCastException ignored) {
            }
            try {
                return ((Listing)request).contract.marketplace.id;
            } catch (ClassCastException ignored) {
            }
        } catch (NullPointerException ignored) {
        }
        return null;
    }

    private static String getProductId(IdModel request) {
        try {
            try {
                return ((AssetRequest)request).asset.product.id;
            } catch (ClassCastException ignored) {
            }
            try {
                return ((TierConfigRequest)request).configuration.product.id;
            } catch (ClassCastException ignored) {
            }
            try {
                return ((Listing)request).product.id;
            } catch (ClassCastException ignored) {
            }
        } catch (NullPointerException ignored) {
        }
        return null;
    }

    private static String getTierAccountId(IdModel request) {
        try {
            try {
                return ((AssetRequest)request).asset.tiers.customer.id;
            } catch (ClassCastException ignored) {
            }
            try {
                return ((TierConfigRequest)request).configuration.account.id;
            } catch (ClassCastException ignored) {
            }
        } catch (NullPointerException ignored) {
        }
        return null;
    }

    private static String getConnectionId(IdModel request) {
        try {
            try {
                return ((AssetRequest)request).asset.connection.id;
            } catch (ClassCastException ignored) {
            }
            try {
                return ((TierConfigRequest)request).configuration.connection.id;
            } catch (ClassCastException ignored) {
            }
        } catch (NullPointerException ignored) {
        }
        return null;
    }

    private static String getVendorId(IdModel request) {
        try {
            try {
                return ((AssetRequest)request).asset.connection.vendor.id;
            } catch (ClassCastException ignored) {
            }
            try {
                return ((TierConfigRequest)request).configuration.connection.vendor.id;
            } catch (ClassCastException ignored) {
            }
            try {
                return ((Listing)request).vendor.id;
            } catch (ClassCastException ignored) {
            }
        } catch (NullPointerException ignored) {
        }
        return null;
    }

    private static String getExternalId(IdModel request) {
        try {
            try {
                return ((AssetRequest)request).asset.externalId;
            } catch (ClassCastException ignored) {
            }
            try {
                return ((TierConfigRequest)request).configuration.connection.provider.id;
            } catch (ClassCastException ignored) {
            }
            try {
                return ((Listing)request).provider.id;
            } catch (ClassCastException ignored) {
            }
        } catch (NullPointerException ignored) {
        }
        return null;
    }

    @Override
    public Object __hx_lookupField(String s, boolean b, boolean b1) {
        return null;
    }

    @Override
    public double __hx_lookupField_f(String s, boolean b) {
        return 0;
    }

    @Override
    public Object __hx_lookupSetField(String s, Object o) {
        return null;
    }

    @Override
    public double __hx_lookupSetField_f(String s, double v) {
        return 0;
    }

    @Override
    public double __hx_setField_f(String s, double v, boolean b) {
        return 0;
    }

    @Override
    public Object __hx_setField(String s, Object o, boolean b) {
        return null;
    }

    @Override
    public Object __hx_getField(String s, boolean b, boolean b1, boolean b2) {
        return null;
    }

    @Override
    public double __hx_getField_f(String s, boolean b, boolean b1) {
        return 0;
    }

    @Override
    public Object __hx_invokeField(String s, Object[] objects) {
        return null;
    }

    @Override
    public void __hx_getFields(Array<String> array) {}
}
