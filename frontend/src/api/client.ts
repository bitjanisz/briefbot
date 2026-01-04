export type HttpMethod = 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE';

export interface RequestOptions extends RequestInit {
  headers?: Record<string, string>;
  parse?: 'json' | 'text' | 'auto';
}

const defaultHeaders: Record<string, string> = {
  'Content-Type': 'application/json',
  Accept: 'application/json, text/html;q=0.5',
};

export async function http<T = unknown>(
  url: string,
  method: HttpMethod = 'GET',
  body?: unknown,
  options: RequestOptions = {}
): Promise<{ ok: boolean; status: number; data: T | null; isHtml?: boolean }> {
  const init: RequestInit = {
    method,
    credentials: 'include',
    ...options,
    headers: {
      ...defaultHeaders,
      ...(options.headers || {}),
    },
    body: body !== undefined ? JSON.stringify(body) : undefined,
  };

  const res = await fetch(url, init);

  const contentType = res.headers.get('content-type') || '';
  const isHtml = contentType.includes('text/html');

  let data: unknown = null;
  try {
    if (options.parse === 'text' || (options.parse !== 'json' && isHtml)) {
      data = await res.text();
    } else if (options.parse === 'json' || contentType.includes('application/json')) {
      data = await res.json();
    } else {
      data = await res.text();
    }
  } catch {
    data = null;
  }

  return { ok: res.ok, status: res.status, data: data as T | null, isHtml };
}
