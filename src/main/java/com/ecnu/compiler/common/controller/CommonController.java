/**
 * Controller编写规则：
 *   1、所有路径均按节划分，每一节均为 单个 名词
 *   2、路径结尾以反斜杠结束
 *   3、符合Restful接口规范
 *        GET：请求资源，不修改服务器数据
 *        POST：向服务器新增资源或修改资源
 *        DELETE：删除服务器资源
 *   4、返回结果均以Resp对象返回，框架统一转换为json
 *   5、代码中不得出现汉字，返回信息统一规范到HttpRespCode中
 *      若Resp中没有所需要的文字，请在ConstantsMsg中添加文字
 *   6、所有函数请在头部标明作者，以便代码回溯
 *   7、使用 @Resource 注解自动装配 Service
 *
 *   @author Michael Chen
 */
package com.ecnu.compiler.common.controller;

import com.ecnu.compiler.common.domain.DfaVO;
import com.ecnu.compiler.common.domain.NfaVO;
import com.ecnu.compiler.common.service.CommonService;
import com.ecnu.compiler.rbac.domain.Compiler;
import com.ecnu.compiler.rbac.domain.User;
import com.ecnu.compiler.rbac.service.UserService;
import com.ecnu.compiler.utils.UserUtils;
import com.ecnu.compiler.utils.domain.HttpRespCode;
import com.ecnu.compiler.utils.domain.Resp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michael Chen
 */
@RestController
@RequestMapping(value = "/common")
public class CommonController {
    @Resource
    private CommonService commonService;
    @Resource
    private UserService userService;

    @RequestMapping(value = "/re2nfa/", method = RequestMethod.GET)
    public ResponseEntity<Resp> RE2NFA(@RequestParam("re") String re) {
        //params error
        if(ObjectUtils.isEmpty(re)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Resp());
        }
        NfaVO nfa = commonService.RE2NFA(re);
        if(ObjectUtils.isEmpty(nfa)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Resp());
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(new Resp(HttpRespCode.SUCCESS,nfa));
        }
    }

    @RequestMapping(value = "/rank/", method = RequestMethod.GET)
    public ResponseEntity<Resp> getSiteRank() {
        return ResponseEntity.status(HttpStatus.OK).body(new Resp(HttpRespCode.SUCCESS,commonService.getSiteRank()));
    }

    @RequestMapping(value = "/parser/", method = RequestMethod.GET)
    public ResponseEntity<Resp> getParserTable(@RequestParam("id")Integer compilerId) {
        if(ObjectUtils.isEmpty(compilerId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Resp());
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Resp(HttpRespCode.SUCCESS,commonService.getParserTable(compilerId)));
    }



    @RequestMapping(value = "/re2dfa/", method = RequestMethod.GET)
    public ResponseEntity<Resp> RE2DFA(@RequestParam("re") String re) {
        //params error
        if(ObjectUtils.isEmpty(re)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Resp());
        }
        DfaVO dfa = commonService.RE2DFA(re);
        if(ObjectUtils.isEmpty(dfa)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Resp());
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(new Resp(HttpRespCode.SUCCESS,dfa));
        }
    }



    @RequestMapping(value = "/compiler/option/", method = RequestMethod.GET)
    public ResponseEntity<Resp> getCompilers() {
        List<Compiler> systemCompilers = commonService.getSystemCompilers();
        User user = UserUtils.getCurrentUser();
        List<Compiler> userCompilers = new ArrayList<>();
        if(user !=null&&user.getId()!=null){
            userCompilers = userService.getUserCompilers(user.getId());
        }
        systemCompilers.addAll(userCompilers);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Resp(HttpRespCode.SUCCESS,systemCompilers));
    }

    @RequestMapping(value = "/system/compiler/", method = RequestMethod.GET)
    public ResponseEntity<Resp> getSystemCompilers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Resp(HttpRespCode.SUCCESS,commonService.getSystemCompilers()));
    }

    @RequestMapping(value = "/system/configuration/", method = RequestMethod.GET)
    public ResponseEntity<Resp> getSystemCompilerConfiguration(@RequestParam("id")Integer id) {
        if(ObjectUtils.isEmpty(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Resp());
        }
        Resp resp = commonService.getCompilerConfiguration(id);
        if(resp.getResCode().equals(HttpRespCode.UNAUTHORIZED.getCode())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Resp());
        } else if(resp.getResCode().equals(HttpRespCode.FORBIDDEN.getCode())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Resp());
        } else if (resp.getResCode().equals(HttpRespCode.NOT_FOUND)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Resp());
        }
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }
}
