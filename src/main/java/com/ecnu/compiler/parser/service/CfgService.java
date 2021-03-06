package com.ecnu.compiler.parser.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ecnu.compiler.common.domain.Cfg;
import com.ecnu.compiler.parser.mapper.CFGMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author michaelchen
 */
@Service
@Transactional
public class CfgService extends ServiceImpl<CFGMapper,Cfg> {

}
