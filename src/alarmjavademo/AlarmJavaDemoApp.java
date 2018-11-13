/*
 * AlarmJavaDemoApp.java
 */

package alarmjavademo;

import alarmjavademo.Card.FACE_INFO;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.examples.win32.W32API;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * The main class of the application.
 */
public class AlarmJavaDemoApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     *
     * @return the instance of AlarmJavaDemoApp
     */
    public static AlarmJavaDemoApp getApplication() {
        return Application.getInstance(AlarmJavaDemoApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(AlarmJavaDemoApp.class, args);
    }

    /*
     * 初始化sdk
     * */
    boolean initSu = hCNetSDK.NET_DVR_Init();
    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    public static NativeLong lUserID = new NativeLong(-1);

    @Override
    protected void startup() {
        /*
         * 登陆
         * */
        if (lUserID.longValue() > -1) {
            //先注销
            hCNetSDK.NET_DVR_Logout(lUserID);
            lUserID = new NativeLong(-1);
        }

        HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
        lUserID = hCNetSDK.NET_DVR_Login_V30("192.168.40.26", (short) 8000, "admin", "hik12345", m_strDeviceInfo);

        long userID = lUserID.longValue();
        System.out.println(userID);
        //====================================================================================
        /*
         * 创建卡号下发长连接
         * */
//        NativeLong cardSendFtpFlag = buildSendCardTcpCon();
//
//        // 设置卡参数
//        HCNetSDK.NET_DVR_CARD_CFG_V50 struCardInfo = new HCNetSDK.NET_DVR_CARD_CFG_V50(); //卡参数
//        struCardInfo.read();
//        struCardInfo.dwSize = struCardInfo.size();
//        struCardInfo.dwModifyParamType = 0x00000001 + 0x00000002 + 0x00000004 + 0x00000008 +
//                0x00000010 + 0x00000020 + 0x00000080 + 0x00000100 + 0x00000200 + 0x00000400 + 0x00000800;
//        /*
//         * #define CARD_PARAM_CARD_VALID       0x00000001  //卡是否有效参数
//         * #define CARD_PARAM_VALID            0x00000002  //有效期参数
//         * #define CARD_PARAM_CARD_TYPE        0x00000004  //卡类型参数
//         * #define CARD_PARAM_DOOR_RIGHT       0x00000008  //门权限参数
//         * #define CARD_PARAM_LEADER_CARD      0x00000010  //首卡参数
//         * #define CARD_PARAM_SWIPE_NUM        0x00000020  //最大刷卡次数参数
//         * #define CARD_PARAM_GROUP            0x00000040  //所属群组参数
//         * #define CARD_PARAM_PASSWORD         0x00000080  //卡密码参数
//         * #define CARD_PARAM_RIGHT_PLAN       0x00000100  //卡权限计划参数
//         * #define CARD_PARAM_SWIPED_NUM       0x00000200  //已刷卡次数
//         * #define CARD_PARAM_EMPLOYEE_NO      0x00000400  //工号
//         * #define CARD_PARAM_NAME             0x00000800  //姓名
//         */
//
//        for (int i = 0; i < HCNetSDK.ACS_CARD_NO_LEN; i++) {
//            struCardInfo.byCardNo[i] = 0;
//        }
////        System.arraycopy(cardNo.getBytes(), 0, struCardInfo.byCardNo, 0, cardNo.length());
//
//        struCardInfo.byCardValid = 1;
//        struCardInfo.wRoomNumber = 302;
//        struCardInfo.byCardType = 1;
//        struCardInfo.byLeaderCard = 0;
//        struCardInfo.byDoorRight[0] = 1; //门1有权限
//        struCardInfo.wCardRightPlan[0].wRightPlan[0] = 1; //门1关联卡参数计划模板1
//
//        //卡有效期
//        struCardInfo.struValid.byEnable = 0;
//
//        struCardInfo.dwMaxSwipeTime = 0; //无次数限制
//        struCardInfo.dwSwipeTime = 0; //已刷卡次数
//        struCardInfo.byCardPassword = "12345678".getBytes();
//        //设置卡号
//        try {
//            byte[] cardNoBytes = "12345".getBytes(); //卡号
//            System.arraycopy(cardNoBytes, 0, struCardInfo.byCardNo, 0, cardNoBytes.length);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        // 设置卡片名称
//        try {
//            byte[] nameBytes = "林志强".getBytes("GBK");
//            System.arraycopy(nameBytes, 0, struCardInfo.byName, 0, nameBytes.length);
////            System.arraycopy(nameBytes, 0, struCardInfo.byName, 0, nameBytes.length);
//        } catch (UnsupportedEncodingException e) {
//            System.out.println("card name get bytes error :" + e);
//        }
//
//        struCardInfo.write();
//        Pointer pSendBufSet = struCardInfo.getPointer();
//
//        // 发送卡信息
//        if (!hCNetSDK.NET_DVR_SendRemoteConfig(cardSendFtpFlag, 0x3, pSendBufSet, struCardInfo.size())) {
//            System.out.println("ENUM_ACS_SEND_DATA失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
//        }
//        return conFlag;
//        下发人脸
//        NativeLong conFaceFlag = buildFaceSetCon("12345");
//        HCNetSDK.NET_DVR_FACE_PARAM_CFG config = new HCNetSDK.NET_DVR_FACE_PARAM_CFG();
//        config.read();
//        config.dwSize = config.size();
//        config.byCardNo = "12345".getBytes();
////        config.byFaceDataType = 1;
//
//        byte[] picFile = "C:\\EntranceGuard\\test.jpg".getBytes();
//        int picDataLength = picFile.length;
//        HCNetSDK.BYTE_ARRAY ptrPicByte = new HCNetSDK.BYTE_ARRAY(picDataLength);
//        ptrPicByte.byValue = picFile;
//        ptrPicByte.write();
//        config.dwFaceLen = picDataLength;
//        config.pFaceBuffer = ptrPicByte.getPointer();
//
//        config.byEnableCardReader[0] = 1;
//        config.byFaceID = 1;
//        config.byFaceDataType = 1;
//        config.write();
//
//        if (!hCNetSDK.NET_DVR_SendRemoteConfig(conFaceFlag, HCNetSDK.ENUM_ACS_INTELLIGENT_IDENTITY_DATA, config.getPointer(), config.size())) {
//            System.out.println("NET_DVR_SendRemoteConfig失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
//        }


        HCNetSDK.NET_DVR_FACE_PARAM_COND lpInBuffer = new HCNetSDK.NET_DVR_FACE_PARAM_COND();

        String cardNo = "12345";

        for (int i = 0; i < cardNo.length(); i++) {

            lpInBuffer.byCardNo[i] = (byte) cardNo.charAt(i);

        }

        lpInBuffer.dwFaceNum = 1;

        lpInBuffer.byFaceID = (byte) 1;

        lpInBuffer.byEnableCardReader = new byte[HCNetSDK.MAX_CARD_READER_NUM_512];

        lpInBuffer.byEnableCardReader[0] = 1;

        lpInBuffer.dwSize = lpInBuffer.size();

        HCNetSDK.FRemoteConfigCallback fRemoteConfigCallback = new HCNetSDK.FRemoteConfigCallback() {
            @Override
            public void invoke(int dwType, Pointer lpBuffer, int dwBufLen, Pointer pUserData) {

            }
        };

        lpInBuffer.write();

        // 启动远程配置。

        NativeLong lHandle = hCNetSDK.NET_DVR_StartRemoteConfig(lUserID, HCNetSDK.NET_DVR_SET_FACE_PARAM_CFG,

                lpInBuffer.getPointer(), lpInBuffer.size(), fRemoteConfigCallback, null);

        System.out.println(hCNetSDK.NET_DVR_GetLastError());

        lpInBuffer.read();

        // 发送长连接数据

        HCNetSDK.NET_DVR_FACE_PARAM_CFG pSendBuf = new HCNetSDK.NET_DVR_FACE_PARAM_CFG();

        for (int i = 0; i < cardNo.length(); i++) {

            pSendBuf.byCardNo[i] = (byte) cardNo.charAt(i);

        }

        // pSendBuf.byCardNo = new byte[] { 1, 2, 4, 6, 7 };

        FACE_INFO faceInfo = new FACE_INFO();

        byte[] byteFace = readPic7();

        faceInfo.byFaceInfo = byteFace;

        faceInfo.write();

        pSendBuf.pFaceBuffer = faceInfo.getPointer();

        pSendBuf.dwFaceLen = byteFace.length;
        pSendBuf.byEnableCardReader = new byte[HCNetSDK.MAX_CARD_READER_NUM_512];

        pSendBuf.byEnableCardReader[0] = 1;

        pSendBuf.byFaceID = (byte) 1;

        pSendBuf.byFaceDataType = (byte) 1;

        pSendBuf.dwSize = pSendBuf.size();

        pSendBuf.write();

        boolean result = hCNetSDK.NET_DVR_SendRemoteConfig(lHandle, HCNetSDK.ENUM_ACS_INTELLIGENT_IDENTITY_DATA,

                pSendBuf.getPointer(), pSendBuf.size());
    }


    /*
     * 下发卡号
     * */
    public static void noticeCardSet(int dwType, Pointer lpBuffer, int dwBufLen, Pointer pUserData, NativeLong connFlag) {
        switch (dwType) {
            case 0:// NET_SDK_CALLBACK_TYPE_STATUS
                HCNetSDK.REMOTECONFIGSTATUS_CARD struCardStatus = new HCNetSDK.REMOTECONFIGSTATUS_CARD();
                struCardStatus.write();
                Pointer pInfoV30 = struCardStatus.getPointer();
                pInfoV30.write(0, lpBuffer.getByteArray(0, struCardStatus.size()), 0, struCardStatus.size());
                struCardStatus.read();

                int iStatus = 0;
                for (int i = 0; i < 4; i++) {
                    int ioffset = i * 8;
                    int iByte = struCardStatus.byStatus[i] & 0xff;
                    iStatus = iStatus + (iByte << ioffset);
                }

                String cardNoStr = new String(struCardStatus.byCardNum).trim();
                switch (iStatus) {
                    case 1000:// NET_SDK_CALLBACK_STATUS_SUCCESS
                        System.out.println("下发卡参数成功" + iStatus);
                        break;
                    case 1001:
                        System.out.println("正在下发卡参数中,dwStatus:" + iStatus);
                        System.out.println("byCardNum : {}" + cardNoStr);
                        break;
                    case 1002:
                        int iErrorCode = 0;
                        for (int i = 0; i < 4; i++) {
                            int ioffset = i * 8;
                            int iByte = struCardStatus.byErrorCode[i] & 0xff;
                            iErrorCode = iErrorCode + (iByte << ioffset);
                        }
                        System.out.println("下发卡参数失败, dwStatus: " + iStatus + " 错误号: " + hCNetSDK.NET_DVR_GetLastError());
                        break;
                }
                break;
            default:
                System.out.println("go card send default process ");
                break;
        }
    }

    /**
     * 创建卡号下发的长连接
     *
     * @param
     * @return
     */
    private NativeLong buildSendCardTcpCon() {
        HCNetSDK.NET_DVR_CARD_CFG_COND m_struCardInputParamSet = new HCNetSDK.NET_DVR_CARD_CFG_COND();
        m_struCardInputParamSet.read();
        m_struCardInputParamSet.dwSize = m_struCardInputParamSet.size();
        m_struCardInputParamSet.dwCardNum = 1;
        m_struCardInputParamSet.byCheckCardNo = 1;

        Pointer lpInBuffer = m_struCardInputParamSet.getPointer();
        m_struCardInputParamSet.write();

        Pointer pUserData = null;
        CardSendHandler cardSendHandler = new CardSendHandler();
        NativeLong conFlag = hCNetSDK.NET_DVR_StartRemoteConfig(lUserID, HCNetSDK.NET_DVR_SET_CARD_CFG_V50, lpInBuffer, m_struCardInputParamSet.size(), cardSendHandler, pUserData);
        System.out.println(conFlag.longValue());
        return conFlag;
    }

    class CardSendHandler implements HCNetSDK.FRemoteConfigCallback {
        @Override
        public void invoke(int dwType, Pointer lpBuffer, int dwBufLen, Pointer pUserData) {
            AlarmJavaDemoApp.noticeCardSet(dwType, lpBuffer, dwBufLen, pUserData, lUserID);
        }
    }

    //=================================================================================
    /*
     * 启动人脸下发的长连接
     * */
    private NativeLong buildFaceSetCon(String cardNo) {
        FaceSendHandler faceSendHandler = new FaceSendHandler();
        HCNetSDK.NET_DVR_FACE_PARAM_COND cond = new HCNetSDK.NET_DVR_FACE_PARAM_COND();
        cond.dwSize = cond.size();
        cond.byCardNo = cardNo.getBytes();
        cond.byEnableCardReader[0] = 1;
        cond.dwFaceNum = 1;
        cond.byFaceID = 1;
        cond.write();

        Pointer lpInBuffer = cond.getPointer();
        return hCNetSDK.NET_DVR_StartRemoteConfig(lUserID, HCNetSDK.NET_DVR_SET_FACE_PARAM_CFG, lpInBuffer, cond.size(), faceSendHandler, null);
    }

    /*
     * 人脸下发回调类
     * */
    class FaceSendHandler implements HCNetSDK.FRemoteConfigCallback {
        @Override
        public void invoke(int dwType, Pointer lpBuffer, int dwBufLen, Pointer pUserData) {
            AlarmJavaDemoApp.noticeFaceSet(dwType, lpBuffer, dwBufLen, pUserData);
        }
    }

    /*
     * 人脸下发回调
     * */
    public static void noticeFaceSet(int dwType, Pointer lpBuffer, int dwBufLen, Pointer pUserData) {
        System.out.println("长连接回调获取数据,NET_SDK_CALLBACK_TYPE_STATUS:" + dwType);
        switch (dwType) {
            case 0:// NET_SDK_CALLBACK_TYPE_STATUS
                HCNetSDK.REMOTECONFIGSTATUS_CARD struCfgStatus = new HCNetSDK.REMOTECONFIGSTATUS_CARD();
                struCfgStatus.write();
                Pointer pCfgStatus = struCfgStatus.getPointer();
                pCfgStatus.write(0, lpBuffer.getByteArray(0, struCfgStatus.size()), 0, struCfgStatus.size());
                struCfgStatus.read();

                int iStatus = 0;
                for (int i = 0; i < 4; i++) {
                    int ioffset = i * 8;
                    int iByte = struCfgStatus.byStatus[i] & 0xff;
                    iStatus = iStatus + (iByte << ioffset);
                }

                switch (iStatus) {
                    case 1000:// NET_SDK_CALLBACK_STATUS_SUCCESS
                        System.out.println("下发人脸参数成功,dwStatus:" + hCNetSDK.NET_DVR_GetLastError());
                        break;
                    case 1001:
                        System.out.println("正在下发人脸参数中,dwStatus:" + hCNetSDK.NET_DVR_GetLastError());
                        break;
                    case 1002:
                        int iErrorCode = 0;
                        for (int i = 0; i < 4; i++) {
                            int ioffset = i * 8;
                            int iByte = struCfgStatus.byErrorCode[i] & 0xff;
                            iErrorCode = iErrorCode + (iByte << ioffset);
                        }
                        System.out.println((Arrays.toString(struCfgStatus.byErrorCode)));
                        System.out.println("下发人脸参数失败, dwStatus:" + iStatus + hCNetSDK.NET_DVR_GetLastError());
                        break;
                }

                break;
            case 2:// 获取状态数据
                HCNetSDK.NET_DVR_FACE_PARAM_STATUS m_struFaceStatus = new HCNetSDK.NET_DVR_FACE_PARAM_STATUS();
                m_struFaceStatus.write();
                Pointer pStatusInfo = m_struFaceStatus.getPointer();
                pStatusInfo.write(0, lpBuffer.getByteArray(0, m_struFaceStatus.size()), 0, m_struFaceStatus.size());
                m_struFaceStatus.read();
                String str = new String(m_struFaceStatus.byCardNo).trim();
                System.out.println("下发人脸数据关联的卡号:" + str + ",人脸读卡器状态:" +
                        m_struFaceStatus.byCardReaderRecvStatus[0] + ",错误描述:" + new String(m_struFaceStatus.byErrorMsg).trim());
            default:
                break;
        }
    }

    public byte[] readPic7() {

        try {

            FileInputStream inputStream = new FileInputStream("C:/EntranceGuard/test.jpg");

            int i = inputStream.available();

            // byte数组用于存放图片字节数据

            byte[] buff = new byte[i];

            inputStream.read(buff);
            // 关闭输入流

            inputStream.close();

            return buff;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }
}
