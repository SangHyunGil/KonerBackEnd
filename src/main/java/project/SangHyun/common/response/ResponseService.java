package project.SangHyun.common.response;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.dto.response.MultipleResult;
import project.SangHyun.dto.response.Result;
import project.SangHyun.dto.response.SingleResult;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ResponseService {

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

    public <E, D> List<D> convertToControllerDto(List<E> object, Function<E, D> convertMethod) {
        return object.stream()
                .map(convertMethod::apply)
                .collect(Collectors.toList());
    }
}
