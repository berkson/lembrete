export class Additive {
  constructor(private _id: number, private _deadline: number) {}

  public get deadline(): number {
    return this._deadline;
  }
  public set deadline(value: number) {
    this._deadline = value;
  }
  public get id(): number {
    return this._id;
  }
  public set id(value: number) {
    this._id = value;
  }

  toJSON() {
    return { contract_id: this.id, deadline: this.deadline };
  }
}
