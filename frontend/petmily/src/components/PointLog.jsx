import { shape, number, string, instanceOf, arrayOf } from 'prop-types';
import { priceToString } from 'utils/utils';

function PointLog({ logs }) {
  console.log('pointLog', logs);
  return (
    <div className="rounded-11xl bg-white min-w-[350px] h-fit overflow-hidden shrink-0 flex flex-col basis-1/4 p-4 box-border items-center justify-start gap-[6px]">
      <div className="flex flex-col items-start justify-center gap-[17px] w-full">
        <div className="relative font-semibold">포인트 내역</div>
        <div className="relative bg-whitesmoke-200 w-full h-px" />
      </div>
      {logs ? (
        logs.map((log, idx) => {
          return (
            <div className="self-stretch flex flex-col items-start justify-start gap-[6px]">
              <div className="self-stretch rounded-11xl bg-white flex flex-row p-3 items-start justify-start gap-[24px]">
                <img
                  className="self-stretch relative max-h-full w-[3px]"
                  alt=""
                  src="/curation-title-line.svg"
                />
                <div className="w-[319px] flex flex-col items-start justify-start gap-[12px]">
                  <div className="self-stretch flex flex-row items-center justify-start gap-[6px]">
                    <div
                      className={`relative tracking-[0.05em] leading-[125%] font-semibold ${
                        log.pointType ? 'text-dodgerblue' : 'text-red'
                      }`}
                    >
                      {log.pointType
                        ? `+${priceToString(log.pointCost)}`
                        : `-${priceToString(log.pointCost)}`}
                    </div>
                    <div className="relative text-base tracking-[0.01em] leading-[125%] font-semibold">
                      포인트가 {log.pointType ? '지급' : '차감'}되었습니다.
                    </div>
                  </div>
                  <div className="self-stretch relative text-base tracking-[0.01em] leading-[125%] text-slategray">
                    {log.pointContent}
                  </div>
                </div>
              </div>
              {idx < logs.length - 1 && (
                <div className="relative bg-whitesmoke-200 w-full h-px" />
              )}
            </div>
          );
        })
      ) : (
        <div className="text-center">
          <p>사용 내역이 없습니다.</p>
        </div>
      )}
    </div>
  );
}

const logType = shape({
  type: true,
  point: number,
  message: string,
  date: instanceOf(Date),
});

PointLog.propTypes = {
  logs: arrayOf(logType),
};

export default PointLog;
