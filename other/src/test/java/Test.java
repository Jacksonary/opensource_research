import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author weiguo.liu
 * @date 2020/10/12
 * @description
 */
public class Test {

    public static void main(String[] args) {
        floatTest();
    }

    public static void floatTest() {
        float a = 1f;
        float b = 0.9f;
        float f = a - b;
        System.out.println(f);

        InetAddress internalIp = getInternalIp();
        System.out.println(internalIp);
    }

    private static InetAddress getInternalIp() {
        String ip = getProperty("host.ip");

        if (ip != null) {
            try {
                return InetAddress.getByName(ip);
            } catch (Exception ignored) {
                System.out.println("ignore...");
                // ignore
            }
        }

        try {
            List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
            List<InetAddress> addresses = new ArrayList<InetAddress>();
            InetAddress local = null;

            try {
                for (NetworkInterface ni : nis) {
                    if (ni.isUp()) {
                        addresses.addAll(Collections.list(ni.getInetAddresses()));
                    }
                }
                return findValidateIp(addresses);
            } catch (Exception e) {
                // ignore
            }
        } catch (SocketException e) {
            // ignore
        }
        return null;
    }

    public static InetAddress findValidateIp(List<InetAddress> addresses) {
        InetAddress local = null;
        for (InetAddress address : addresses) {
            if (address instanceof Inet4Address) {
                if (address.isLoopbackAddress() || address.isSiteLocalAddress()) {
                    if (local == null) {
                        local = address;
                    } else if (address.isSiteLocalAddress() && !address.isLoopbackAddress()) {
                        // site local address has higher priority than other address
                        local = address;
                    } else if (local.isSiteLocalAddress() && address.isSiteLocalAddress()) {
                        // site local address with a host name has higher
                        // priority than one without host name
                        if (local.getHostName().equals(local.getHostAddress())
                            && !address.getHostName().equals(address.getHostAddress())) {
                            local = address;
                        }
                    }
                } else {
                    if (local == null || local.isLoopbackAddress()) {
                        local = address;
                    }
                }
            }
        }
        return local;
    }

    private static String getProperty(String name) {
        String value = System.getProperty(name);

        if (value == null) {
            value = System.getenv(name);
        }

        return value;
    }

}
