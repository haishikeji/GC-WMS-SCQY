package com.px.coinage.core.exception;

import com.px.coinage.core.constants.ResponseEnum;

/**
 * -Token错误异常
 *
 * @author: lug 2021年11月1日
 */
public class TokenErrorException extends RetException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TokenErrorException() {
        super(ResponseEnum.TOKEN_ERROR, ResponseEnum.TOKEN_ERROR.getMsg());
    }

    public TokenErrorException(String msg) {
        super(ResponseEnum.TOKEN_ERROR, msg);
    }

}
