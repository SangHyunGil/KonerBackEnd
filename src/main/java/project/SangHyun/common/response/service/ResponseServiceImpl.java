package project.SangHyun.common.response.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.response.domain.MultipleResult;
import project.SangHyun.common.response.domain.Result;
import project.SangHyun.common.response.domain.SingleResult;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ResponseServiceImpl {

    private static final String SUCCESS_MSG = "요청에 성공하였습니다.";

    public Result getDefaultSuccessResult() {
        return getSuccessResult();
    }

    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        setSuccessResult(result);
        result.setData(data);

        return result;
    }

    public <T> MultipleResult<T> getMultipleResult(List<T> data) {
        MultipleResult<T> result = new MultipleResult<>();
        setSuccessResult(result);
        result.setData(data);

        return result;
    }

    public Result getSuccessResult() {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(0);
        result.setMsg(SUCCESS_MSG);

        return result;
    }

    public void setSuccessResult(Result result) {
        result.setSuccess(true);
        result.setCode(0);
        result.setMsg(SUCCESS_MSG);
    }

    public Result getFailureResult(int code, String msg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);

        return result;
    }
}
