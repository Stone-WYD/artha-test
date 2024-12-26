interface AjaxResult<T> {
  code: number;
  message: string;
  data: T;
}

type Result<T> = Promise<AjaxResult<T>>;
