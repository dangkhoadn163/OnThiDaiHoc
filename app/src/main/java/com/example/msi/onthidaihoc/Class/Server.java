package com.example.msi.onthidaihoc.Class;

/**
 * Created by MSI on 1/31/2018.
 */


public class Server {
    public static String localhost= "192.168.100.6:81";
    public static String URL_LOGIN = "http://" + localhost + "/onthidh/login.php";
    public static String URL_REGISTER = "http://" + localhost + "/onthidh/register.php";
    public static String URL_LOAD = "http://" + localhost + "/onthidh/duongdandendethi.php";
    public static String URL_GETMON = "http://" + localhost + "/onthidh/getnametest.php";
    public static String URL_LOADANSWERUSER = "http://" + localhost + "/onthidh/ketquathi.php";
    public static String URL_GETKETQUATHI = "http://" + localhost + "/onthidh/getketquathi.php";
    public static String URL_GETLISTOLDTEST = "http://" + localhost + "/onthidh/listoldtest.php";
}
