package anhpha.clientfirst.crm.utils;

/**
 * Created by mc975 on 5/20/15.
 */
public class HaversineAlgorithm {

    static final double _eQuatorialEarthRadius = 6378.1370D;
    static final double _d2r = (Math.PI / 180D);

    public static int HaversineInM(double lat1, double long1, double lat2, double long2) {
        if((lat1 == 0 && long1 ==0 )|| (lat2 == 0 && long2 ==0 ))
            return  -1;
        int dt = (int) (1000D * HaversineInKM(lat1, long1, lat2, long2));
//        if(dt  > 0 && dt < 5){
//            dt = 5;
//        }
//        if(dt  > 5 && dt < 10){
//            dt = 10;
//        }
//        if(dt  > 10 && dt < 15){
//            dt = 15;
//        }
//        if(dt  > 15 && dt < 20){
//            dt = 20;
//        }
//        if(dt  > 20 && dt < 30){
//            dt = 30;
//        }
//        if(dt  > 30 && dt < 40){
//            dt = 40;
//        }
//        if(dt  > 40 && dt < 50){
//            dt = 50;
//        }
//        if(dt  > 50 && dt < 60){
//            dt = 60;
//        }
//        if(dt  > 60 && dt < 70){
//            dt = 70;
//        }
//        if(dt  > 70 && dt < 80){
//            dt = 80;
//        }
//        if(dt  > 80 && dt < 90){
//            dt = 90;
//        }

        return dt;
    }

    public static double HaversineInKM(double lat1, double long1, double lat2, double long2) {
        double dlong = (long2 - long1) * _d2r;
        double dlat = (lat2 - lat1) * _d2r;
        double a = Math.pow(Math.sin(dlat / 2D), 2D) + Math.cos(lat1 * _d2r) * Math.cos(lat2 * _d2r)
                * Math.pow(Math.sin(dlong / 2D), 2D);
        double c = 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
        double d = _eQuatorialEarthRadius * c;

        return d;
    }

}