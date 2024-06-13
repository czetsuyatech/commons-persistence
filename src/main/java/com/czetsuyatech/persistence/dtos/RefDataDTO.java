package com.czetsuyatech.persistence.dtos;

import com.czetsuyatech.persistence.Code;
import com.czetsuyatech.persistence.IEnable;
import com.czetsuyatech.persistence.ISorted;
import com.czetsuyatech.persistence.Id;
import java.io.Serializable;

public interface RefDataDTO<T extends Serializable> extends Id<T>, IEnable, ISorted, Code, Serializable {

  RefDataDTO<T> deepClone();
}
