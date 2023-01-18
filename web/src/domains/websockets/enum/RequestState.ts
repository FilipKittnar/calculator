export class RequestState {
  // sent: websockets are connected, request is sent and waiting for reply
  static readonly SENT = new RequestState('SENT');
  // pending: websockets are not connected, will be resent or deleted
  static readonly PENDING = new RequestState('PENDING');

  private constructor(public readonly key: string) {}

  toString(): string {
    return this.key;
  }
}
