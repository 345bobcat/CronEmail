package com.dark.service.template.impl;

import com.dark.model.EmailConfig;
import com.dark.service.base.impl.BaseService;
import com.dark.service.template.EmailConfigService;
import org.springframework.stereotype.Service;

/**
 * Created by jinwenzhang on 17/2/9.
 */
@Service("emailConfigService")
public class EmailConfigServiceImpl extends BaseService<EmailConfig> implements EmailConfigService{
}
