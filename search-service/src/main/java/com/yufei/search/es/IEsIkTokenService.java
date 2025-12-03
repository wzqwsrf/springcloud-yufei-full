package com.yufei.search.es;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangzhenqing
 * @date 2024/01/24 16:02
 * @description
 */
@Service
public interface IEsIkTokenService {

    List<String> getIkSmartTokenList(String query);

    List<String> getIkMaxWordTokenList(String query);

    List<String> getIkSmartTokenListProd(String query);

    List<String> getTokensWithJieba(String query);

    List<String> getTokensWithJiebaWithoutStop(String query);

    List<String> getTokensWithJiebaList(List<String> queryList);

    List<String> getTokensWithJieba(String query, String index);

    List<String> getStandardTokenList(String query);
}
