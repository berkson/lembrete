import { CodeVerifyDTO } from './codeverifydto';

describe('Codeverifydto', () => {
  it('should create an instance', () => {
    expect(
      new CodeVerifyDTO('56489773015', 'TIv@jiY8Vk', '123456')
    ).toBeTruthy();
  });
});
