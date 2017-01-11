package com.mtel.location.bean;

import java.util.List;

/**
 * Created by mtelnet on 16/8/26.
 */
public class GoogleBean {

    /**
     * address_components : [{"long_name":"13","short_name":"13","types":["street_number"]},{"long_name":"新街","short_name":"新街","types":["route"]},{"long_name":"太平山","short_name":"太平山","types":["neighborhood","political"]},{"long_name":"香港島","short_name":"香港島","types":["administrative_area_level_1","political"]},{"long_name":"香港","short_name":"HK","types":["country","political"]}]
     * formatted_address : 香港太平山新街13號
     * geometry : {"location":{"lat":22.286004,"lng":114.146691},"location_type":"ROOFTOP","viewport":{"northeast":{"lat":22.2873529802915,"lng":114.1480399802915},"southwest":{"lat":22.2846550197085,"lng":114.1453420197085}}}
     * place_id : ChIJfabJIX8ABDQReWq_2Yi8bjc
     * types : ["street_address"]
     */

    private String formatted_address;
    /**
     * location : {"lat":22.286004,"lng":114.146691}
     * location_type : ROOFTOP
     * viewport : {"northeast":{"lat":22.2873529802915,"lng":114.1480399802915},"southwest":{"lat":22.2846550197085,"lng":114.1453420197085}}
     */

    private GeometryBean geometry;
    private String place_id;
    /**
     * long_name : 13
     * short_name : 13
     * types : ["street_number"]
     */

    private List<AddressComponentsBean> address_components;
    private List<String> types;

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public GeometryBean getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryBean geometry) {
        this.geometry = geometry;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public List<AddressComponentsBean> getAddress_components() {
        return address_components;
    }

    public void setAddress_components(List<AddressComponentsBean> address_components) {
        this.address_components = address_components;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public static class GeometryBean {
        /**
         * lat : 22.286004
         * lng : 114.146691
         */

        private LocationBean location;
        private String location_type;
        /**
         * northeast : {"lat":22.2873529802915,"lng":114.1480399802915}
         * southwest : {"lat":22.2846550197085,"lng":114.1453420197085}
         */

        private ViewportBean viewport;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getLocation_type() {
            return location_type;
        }

        public void setLocation_type(String location_type) {
            this.location_type = location_type;
        }

        public ViewportBean getViewport() {
            return viewport;
        }

        public void setViewport(ViewportBean viewport) {
            this.viewport = viewport;
        }

        public static class LocationBean {
            private double lat;
            private double lng;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }
        }

        public static class ViewportBean {
            /**
             * lat : 22.2873529802915
             * lng : 114.1480399802915
             */

            private NortheastBean northeast;
            /**
             * lat : 22.2846550197085
             * lng : 114.1453420197085
             */

            private SouthwestBean southwest;

            public NortheastBean getNortheast() {
                return northeast;
            }

            public void setNortheast(NortheastBean northeast) {
                this.northeast = northeast;
            }

            public SouthwestBean getSouthwest() {
                return southwest;
            }

            public void setSouthwest(SouthwestBean southwest) {
                this.southwest = southwest;
            }

            public static class NortheastBean {
                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            public static class SouthwestBean {
                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }
        }
    }

    public static class AddressComponentsBean {
        private String long_name;
        private String short_name;
        private List<String> types;

        public String getLong_name() {
            return long_name;
        }

        public void setLong_name(String long_name) {
            this.long_name = long_name;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }
    }
}
