import { ValidationError } from './validationerror.model';

describe('Validationerror', () => {
  it('should create an instance', () => {
    expect(
      new ValidationError(400, 'BAD REQUEST', '/test', ['field invalalid'])
    ).toBeTruthy();
  });
});
