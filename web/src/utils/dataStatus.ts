export const LOADING = 'loading';
export type NO_DATA_STATE = undefined | typeof LOADING;

export function hasData<T>(value: T | NO_DATA_STATE): value is T {
  return value !== LOADING && value !== undefined;
}

export function getData<T>(value: T | NO_DATA_STATE): T | undefined {
  return hasData(value) ? value : undefined;
}

export function isLoading<T>(value: T | NO_DATA_STATE): value is typeof LOADING {
  return value === LOADING;
}
