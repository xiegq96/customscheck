package cn.bobo.fast.modules.base.utils;



public class CommonReturnUtil {

    /**
     * 返回一个returnData为空对象的成功消息的json
     *
     * @return
     */
    public static ReturnData success() {
        return success(null);
    }

    /**
     * 返回一个返回码为100的对象
     * @param result 对象里的主要内容
     * @return
     */
    public static ReturnData success(Object result) {
        ReturnData resultJson = new ReturnData();
        resultJson.setCode(Constants.SUCCESS_CODE);
        resultJson.setMsg(Constants.SUCCESS_MSG);
        resultJson.setResult(result);
        return resultJson;
    }



    /**
     * 返回错误信息
     * @param code 错误码
     * @param msg  错误信息
     * @return
     */
    public static ReturnData error(Integer code,String msg) {
        ReturnData resultJson = new ReturnData();
        resultJson.setCode(code);
        resultJson.setMsg(msg);
        resultJson.setResult(null);
        return resultJson;
    }
    public static ReturnData error() {
        ReturnData resultJson = new ReturnData();
        resultJson.setCode(Constants.FAIL_CODE);
        resultJson.setMsg(Constants.FAIL_MSG);
        resultJson.setResult(null);
        return resultJson;
    }
    /**
     * 运行时错误返回错误信息
     * @param msg  错误信息
     * @return
     */
    public static ReturnData error(String msg) {
       return error(Constants.FAIL_CODE,msg);
    }

 /*   public static ReturnData error(Object msg) {
        ReturnData resultJson = new ReturnData();
        resultJson.setCode(Constants.RUNTIME_ERROR_CODE);
        resultJson.setMsg("执行失败");
        resultJson.setResult(msg);
        return resultJson;
    }*/
}
