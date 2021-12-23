package project.SangHyun;

import project.SangHyun.response.domain.MultipleResult;
import project.SangHyun.response.domain.SingleResult;

import java.util.List;

public class ResponseFactory {
    public static <T> SingleResult<T> makeSingleResult(T responseDto) {
        SingleResult<T> singleResult = new SingleResult<>();
        singleResult.setCode(0); singleResult.setSuccess(true); singleResult.setMsg("성공");
        singleResult.setData(responseDto);
        return singleResult;
    }

    public static <T> MultipleResult<T> makeMultipleResult(List<T> responseDtos) {
        MultipleResult<T> multipleResult = new MultipleResult<>();
        multipleResult.setCode(0); multipleResult.setSuccess(true); multipleResult.setMsg("성공");
        multipleResult.setData(responseDtos);
        return multipleResult;
    }
}
